package com.tracecraft.client.proxy.vulkan;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tracecraft.client.shader.ShaderDefinition;
import com.tracecraft.client.shader.ShaderField;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IGlUniformExt;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IShaderProgramExt;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class ShaderProxy {

    private static final ResourceLocation WHITE_TEXTURE_ID = ResourceLocation.fromNamespaceAndPath("tracecraft",
        "generated/white");
    private static Integer whiteTextureId;

    private ShaderProxy() {
    }

    public static native int registerShader(String shaderKey, int vertexFormatType,
        int drawMode, int uniformSize, String vertexShaderPath, String fragmentShaderPath,
        String[] defineNames, String[] defineValues);

    public static native void draw(int vertexId, int indexId, int shaderId, int indexCount,
        int indexType, long uniformPtr, int uniformSize);

    public static void draw(BufferProxy.VertexIndexBufferHandle handle, int shaderId, int indexCount,
        int indexType, long uniformPtr, int uniformSize) {
        draw(handle.vertexId, handle.indexId, shaderId, indexCount, indexType, uniformPtr,
            uniformSize);
    }

    public static UniformHandle createUniform(ShaderDefinition shader, CompiledShaderProgram shaderProgram,
        MemoryStack stack) {
        ByteBuffer bb = stack.calloc(shader.uniformBufferSize());
        IShaderProgramExt ext = (IShaderProgramExt) (Object) shaderProgram;
        int uniformIndex = 0;
        for (ShaderField field : shader.fields()) {
            if (field.isSampler()) {
                bb.putInt(field.offset(), resolveSamplerTextureId(ext, field));
                continue;
            }
            Uniform uniform = ext.tracecraft$getUniformsValue()
                .get(uniformIndex++);
            putUniform(bb, field, uniform);
        }
        return new UniformHandle(MemoryUtil.memAddress(bb), shader.uniformBufferSize());
    }

    public static void syncState(CompiledShaderProgram shaderProgram, Object drawMode) {
    }

    public record UniformHandle(long addr, int size) {

    }

    private static int resolveSamplerTextureId(IShaderProgramExt ext, ShaderField field) {
        Object2IntMap<String> samplerTextures = ext.tracecraft$getSamplerTexturesValue();
        if (samplerTextures.containsKey(field.name())) {
            int textureId = samplerTextures.getInt(field.name());
            if (textureId != 0) {
                return textureId;
            }
        }
        Integer fallbackSlot = tryParseSamplerSlot(field.name());
        if (fallbackSlot != null) {
            int textureId = RenderSystem.getShaderTexture(fallbackSlot);
            if (textureId != 0) {
                return textureId;
            }
        }
        if (field.samplerSlot() >= 0) {
            int textureId = RenderSystem.getShaderTexture(field.samplerSlot());
            if (textureId != 0) {
                return textureId;
            }
        }
        if ("Sampler2".equals(field.name())) {
            return getWhiteTextureId();
        }
        return 0;
    }

    public static int getWhiteTextureId() {
        Integer cached = whiteTextureId;
        if (cached != null) {
            return cached;
        }

        NativeImage image = new NativeImage(16, 16, false);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                image.setPixel(x, y, 0xFFFFFFFF);
            }
        }
        DynamicTexture texture = new DynamicTexture(image);
        Minecraft.getInstance()
            .getTextureManager()
            .register(WHITE_TEXTURE_ID, texture);
        whiteTextureId = texture.getId();
        return whiteTextureId;
    }

    private static Integer tryParseSamplerSlot(String samplerName) {
        if (!samplerName.startsWith("Sampler")) {
            return null;
        }
        try {
            return Integer.parseInt(samplerName.substring("Sampler".length()));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static void putUniform(ByteBuffer bb, ShaderField field, Uniform uniform) {
        IGlUniformExt ext = (IGlUniformExt) (Object) uniform;
        switch (field.kind()) {
            case INT -> putInts(bb, field.offset(), ext.tracecraft$getIntDataValue(),
                field.componentCount());
            case FLOAT -> putFloats(bb, field.offset(), ext.tracecraft$getFloatDataValue(),
                field.componentCount());
            case MATRIX -> putMatrix(bb, field.offset(), field.componentCount(), uniform.getName(),
                ext.tracecraft$getFloatDataValue());
            case SAMPLER -> throw new IllegalStateException("Sampler fields are written separately");
        }
    }

    private static void putInts(ByteBuffer bb, int offset, IntBuffer values, int componentCount) {
        for (int i = 0; i < componentCount; i++) {
            bb.putInt(offset + i * Integer.BYTES, values.get(i));
        }
    }

    private static void putFloats(ByteBuffer bb, int offset, FloatBuffer values, int componentCount) {
        for (int i = 0; i < componentCount; i++) {
            bb.putFloat(offset + i * Float.BYTES, values.get(i));
        }
    }

    private static void putMatrix(ByteBuffer bb, int offset, int dimension, String uniformName,
        FloatBuffer values) {
        if (dimension == 4) {
            float[] matrix = new float[16];
            for (int i = 0; i < 16; i++) {
                matrix[i] = values.get(i);
            }
            if ("ProjMat".equals(uniformName)) {
                mapProjectionMatrix(matrix);
            }
            for (int i = 0; i < 16; i++) {
                bb.putFloat(offset + i * Float.BYTES, matrix[i]);
            }
            return;
        }

        int columnStride = Float.BYTES * 4;
        for (int column = 0; column < dimension; column++) {
            for (int row = 0; row < dimension; row++) {
                bb.putFloat(offset + column * columnStride + row * Float.BYTES,
                    values.get(column * dimension + row));
            }
        }
    }

    private static void mapProjectionMatrix(float[] matrix) {
        for (int column = 0; column < 4; column++) {
            int base = column * 4;
            float row0 = matrix[base];
            float row1 = matrix[base + 1];
            float row2 = matrix[base + 2];
            float row3 = matrix[base + 3];
            matrix[base] = row0;
            matrix[base + 1] = -row1;
            matrix[base + 2] = row2 * 0.5F + row3 * 0.5F;
            matrix[base + 3] = row3;
        }
    }
}

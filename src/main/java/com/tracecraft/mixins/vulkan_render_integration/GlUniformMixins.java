package com.tracecraft.mixins.vulkan_render_integration;

import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IGlUniformExt;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.gl.GlUniform;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GlUniform.class)
public abstract class GlUniformMixins implements IGlUniformExt {

    @Shadow
    @Final
    private int count;

    @Shadow
    @Final
    private int dataType;

    @Shadow
    @Final
    private IntBuffer intData;

    @Shadow
    @Final
    private FloatBuffer floatData;

    @Override
    public int tracecraft$getDataTypeValue() {
        return this.dataType;
    }

    @Override
    public int tracecraft$getCountValue() {
        return this.count;
    }

    @Override
    public IntBuffer tracecraft$getIntDataValue() {
        return this.intData;
    }

    @Override
    public FloatBuffer tracecraft$getFloatDataValue() {
        return this.floatData;
    }
}

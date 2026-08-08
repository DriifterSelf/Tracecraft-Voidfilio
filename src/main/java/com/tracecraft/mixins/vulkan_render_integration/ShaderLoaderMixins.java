package com.tracecraft.mixins.vulkan_render_integration;

import com.mojang.blaze3d.shaders.CompiledShader;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.ICompiledShaderExt;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IShaderProgramExt;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.client.renderer.ShaderProgramConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShaderManager.class)
public class ShaderLoaderMixins {

    @Inject(method = "linkProgram", at = @At("RETURN"))
    private static void captureProgramMetadata(ShaderProgram key,
        ShaderProgramConfig definition, CompiledShader vertexShader,
        CompiledShader fragmentShader, CallbackInfoReturnable<CompiledShaderProgram> cir) {
        CompiledShaderProgram shaderProgram = cir.getReturnValue();
        IShaderProgramExt ext = (IShaderProgramExt) (Object) shaderProgram;
        ext.tracecraft$setShaderName(key.configId().toString());
        ext.tracecraft$setVertexFormat(key.vertexFormat());
        ext.tracecraft$setVertexSource(
            ((ICompiledShaderExt) (Object) vertexShader).tracecraft$getResolvedSource());
        ext.tracecraft$setFragmentSource(
            ((ICompiledShaderExt) (Object) fragmentShader).tracecraft$getResolvedSource());
    }
}

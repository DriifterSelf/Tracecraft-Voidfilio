package com.tracecraft.mixins.vulkan_render_integration;

import com.mojang.blaze3d.shaders.Uniform;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IGlUniformExt;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Uniform.class)
public abstract class GlUniformMixins implements IGlUniformExt {

    @Shadow
    @Final
    private int count;

    @Shadow
    @Final
    private int type;

    @Shadow
    @Final
    private IntBuffer intValues;

    @Shadow
    @Final
    private FloatBuffer floatValues;

    @Override
    public int tracecraft$getDataTypeValue() {
        return this.type;
    }

    @Override
    public int tracecraft$getCountValue() {
        return this.count;
    }

    @Override
    public IntBuffer tracecraft$getIntDataValue() {
        return this.intValues;
    }

    @Override
    public FloatBuffer tracecraft$getFloatDataValue() {
        return this.floatValues;
    }
}

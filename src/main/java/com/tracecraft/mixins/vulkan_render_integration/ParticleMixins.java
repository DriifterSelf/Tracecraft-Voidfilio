package com.tracecraft.mixins.vulkan_render_integration;

import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IParticleExt;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Particle.class)
public class ParticleMixins implements IParticleExt {

    private String tracecraft$contentName = null;

    @Shadow
    protected double x;

    @Shadow
    protected double y;

    @Shadow
    protected double z;

    @Override
    public double tracecraft$getX() {
        return x;
    }

    @Override
    public double tracecraft$getY() {
        return y;
    }

    @Override
    public double tracecraft$getZ() {
        return z;
    }

    @Override
    public String tracecraft$getContentName() {
        return tracecraft$contentName;
    }

    @Override
    public void tracecraft$setContentName(String contentName) {
        tracecraft$contentName = contentName;
    }
}

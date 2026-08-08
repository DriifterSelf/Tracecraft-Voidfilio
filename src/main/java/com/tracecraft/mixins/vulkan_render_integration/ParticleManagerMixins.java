package com.tracecraft.mixins.vulkan_render_integration;

import static com.tracecraft.client.proxy.world.EntityProxy.PARTICLE_COUNTERS;

import com.llamalad7.mixinextras.sugar.Local;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IParticleManagerExt;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IParticleExt;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixins implements IParticleManagerExt {

    @Final
    @Shadow
    private static List<ParticleRenderType> RENDER_ORDER;
    @Final
    @Shadow
    private Map<ParticleRenderType, Queue<Particle>> particles;

    @Override
    public List<ParticleRenderType> tracecraft$getTextureSheets() {
        return RENDER_ORDER;
    }

    @Override
    public Map<ParticleRenderType, Queue<Particle>> tracecraft$getParticles() {
        return particles;
    }

    @Inject(method = "add(Lnet/minecraft/client/particle/Particle;)V", at = @At(value = "HEAD"))
    public void addParticleCounter(Particle particle, CallbackInfo ci) {
        PARTICLE_COUNTERS.computeIfAbsent(particle.getClass(), k -> new AtomicInteger())
            .incrementAndGet();
    }

    @Inject(method = "tickParticleList(Ljava/util/Collection;)V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
    public void removeParticleCounter(Collection<Particle> particles, CallbackInfo ci,
        @Local Particle particle) {
        AtomicInteger counter = PARTICLE_COUNTERS.get(particle.getClass());
        if (counter != null) {
            counter.decrementAndGet();
        }
    }

    @Inject(method = "createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/particle/ParticleEngine;add(Lnet/minecraft/client/particle/Particle;)V",
            shift = At.Shift.BEFORE),
        cancellable = true)
    public void checkParticleCounter(ParticleOptions parameters,
        double x,
        double y,
        double z,
        double velocityX,
        double velocityY,
        double velocityZ,
        CallbackInfoReturnable<Particle> cir,
        @Local Particle particle) {
        AtomicInteger counter = PARTICLE_COUNTERS.get(particle.getClass());
        if (counter != null) {
            int numParticles = counter.get();
//            if (particle instanceof WaterSuspendParticle) {
//                if (numParticles > 128) {
//                    cir.setReturnValue(null);
//                }
//            } else if (particle instanceof RainSplashParticle || particle instanceof WaterSplashParticle) {
//                if (numParticles > 32) {
//                    cir.setReturnValue(null);
//                }
//            }
        }

        ResourceLocation particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(parameters.getType());
        if (particleId != null) {
            ((IParticleExt) particle).tracecraft$setContentName(
                EntityContentNames.toParticleContentName(particleId));
        }
    }

    private static final class EntityContentNames {

        private static String toParticleContentName(ResourceLocation particleId) {
            if ("minecraft".equals(particleId.getNamespace())) {
                return "/particle/" + particleId.getPath();
            }
            return "/particle/" + particleId.getNamespace() + "/" + particleId.getPath();
        }
    }
}

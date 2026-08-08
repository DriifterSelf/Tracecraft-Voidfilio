package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;

public interface IParticleManagerExt {

    List<ParticleRenderType> tracecraft$getTextureSheets();

    Map<ParticleRenderType, Queue<Particle>> tracecraft$getParticles();
}

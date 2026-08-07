package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;

public interface IParticleManagerExt {

    List<ParticleTextureSheet> tracecraft$getTextureSheets();

    Map<ParticleTextureSheet, Queue<Particle>> tracecraft$getParticles();
}

package net.minecraft.client.particle;

import net.minecraft.client.renderer.RenderType;

public class ParticleRenderType {
    public static final ParticleRenderType CUSTOM = new ParticleRenderType();

    public RenderType renderType() {
        return RenderType.lines();
    }
}

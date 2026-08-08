package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import net.minecraft.client.renderer.RenderType;

public interface IDrawContextExt {

    void tracecraft$drawOrientedQuad(RenderType layer, float x1, float y1, float x2, float y2,
        float thickness, int color);
}

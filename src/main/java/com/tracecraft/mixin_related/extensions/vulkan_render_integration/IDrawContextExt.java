package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import net.minecraft.client.render.RenderLayer;

public interface IDrawContextExt {

    void tracecraft$drawOrientedQuad(RenderLayer layer, float x1, float y1, float x2, float y2,
        float thickness, int color);
}

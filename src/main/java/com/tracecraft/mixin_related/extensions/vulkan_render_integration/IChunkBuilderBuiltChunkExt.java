package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

public interface IChunkBuilderBuiltChunkExt {

    SectionRenderDispatcher tracecraft$getChunkBuilder();
}

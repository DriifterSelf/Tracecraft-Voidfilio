package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.chunk.SectionCompiler;

public interface IChunkBuilderExt {

    SectionCompiler tracecraft$getSectionBuilder();

    ClientLevel tracecraft$getWorld();

    SectionBufferBuilderPack tracecraft$getBuffers();
}

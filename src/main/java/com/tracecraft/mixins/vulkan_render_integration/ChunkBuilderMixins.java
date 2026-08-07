package com.tracecraft.mixins.vulkan_render_integration;

import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IChunkBuilderExt;
import net.minecraft.client.render.chunk.BlockBufferAllocatorStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.SectionBuilder;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkBuilder.class)
public class ChunkBuilderMixins implements IChunkBuilderExt {

    @Final
    @Shadow
    SectionBuilder sectionBuilder;

    @Final
    @Shadow
    BlockBufferAllocatorStorage buffers;

    @Shadow
    ClientWorld world;

    @Override
    public SectionBuilder tracecraft$getSectionBuilder() {
        return sectionBuilder;
    }

    @Override
    public ClientWorld tracecraft$getWorld() {
        return world;
    }

    @Override
    public BlockBufferAllocatorStorage tracecraft$getBuffers() {
        return buffers;
    }
}

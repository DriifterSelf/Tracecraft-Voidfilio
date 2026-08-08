package com.tracecraft.client.vertex;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class StorageVertexConsumerProvider implements MultiBufferSource {

    protected final Map<RenderType, VertexConsumer> pending = new HashMap<>();
    protected final Map<RenderType, ByteBufferBuilder> allocated = new HashMap<>();

    private int size = 0;

    public StorageVertexConsumerProvider(int size) {
        this.size = size;
    }

    private static void assignBufferBuilder(
        Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> builderStorage,
        RenderType layer) {
        builderStorage.put(layer, new ByteBufferBuilder(layer.bufferSize()));
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderLayer) {
        VertexConsumer vertexConsumer = this.pending.get(renderLayer);

        if (vertexConsumer == null) {
            ByteBufferBuilder bufferAllocator = new ByteBufferBuilder(size);
            allocated.put(renderLayer, bufferAllocator);

            vertexConsumer = new PBRVertexConsumer(bufferAllocator, renderLayer);
            this.pending.put(renderLayer, vertexConsumer);
        }
        return vertexConsumer;
    }

    public Map<RenderType, VertexConsumer> getLayers() {
        return this.pending;
    }

    public void close() {
        for (Map.Entry<RenderType, ByteBufferBuilder> entry : this.allocated.entrySet()) {
            entry.getValue()
                .close();
        }
        this.pending.clear();
    }
}
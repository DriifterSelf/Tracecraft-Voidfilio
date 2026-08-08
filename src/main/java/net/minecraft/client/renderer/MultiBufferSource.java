package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;

public interface MultiBufferSource {
    VertexConsumer getBuffer(RenderType renderType);

    static BufferSource immediate(com.mojang.blaze3d.vertex.ByteBufferBuilder builder) {
        return new BufferSource();
    }

    class BufferSource implements MultiBufferSource {
        @Override
        public VertexConsumer getBuffer(RenderType renderType) {
            return null;
        }
        public void endBatch() {}
        public void endBatch(RenderType renderType) {}
        public void endLastBatch() {}
    }
}

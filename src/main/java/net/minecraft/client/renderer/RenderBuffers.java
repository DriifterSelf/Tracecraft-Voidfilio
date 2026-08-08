package net.minecraft.client.renderer;

public class RenderBuffers {
    public MultiBufferSource.BufferSource bufferSource() {
        return new MultiBufferSource.BufferSource();
    }
    public MultiBufferSource.BufferSource crumblingBufferSource() {
        return new MultiBufferSource.BufferSource();
    }
}

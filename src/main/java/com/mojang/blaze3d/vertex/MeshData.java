package com.mojang.blaze3d.vertex;

import java.nio.ByteBuffer;

public class MeshData implements AutoCloseable {
    public static class DrawState {
        public DrawState() {}
        public DrawState(Object format, int vertexCount, int indexCount, Object drawMode, Object indexType) {}
        public Object indexType() { return null; }
        public int indexCount() { return 0; }
        public int vertexCount() { return 0; }
        public VertexFormat format() { return new VertexFormat(); }
        public Object mode() { return null; }
    }

    public MeshData() {}
    public MeshData(Object buf, DrawState drawState) {}

    public DrawState drawState() { return new DrawState(); }
    public ByteBuffer vertexBuffer() { return ByteBuffer.allocate(0); }
    public ByteBuffer indexBuffer() { return ByteBuffer.allocate(0); }

    @Override
    public void close() {}
}

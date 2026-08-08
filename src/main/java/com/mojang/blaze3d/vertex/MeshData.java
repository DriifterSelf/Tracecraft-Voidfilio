package com.mojang.blaze3d.vertex;

import java.nio.ByteBuffer;

public class MeshData {
    public static class DrawState {
        public DrawState() {}
        public DrawState(Object format, int vertexCount, int indexCount, Object drawMode, Object indexType) {}

        public Object mode() { return null; }
        public int indexCount() { return 0; }
        public int vertexCount() { return 0; }
        public Object format() { return null; }
    }

    public MeshData() {}
    public MeshData(Object buf, DrawState drawState) {}

    public DrawState drawState() {
        return new DrawState();
    }

    public ByteBuffer vertexBuffer() { return ByteBuffer.allocate(0); }
    public void close() {}
}

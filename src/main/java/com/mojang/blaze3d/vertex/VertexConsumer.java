package com.mojang.blaze3d.vertex;

public interface VertexConsumer {
    VertexConsumer addVertex(float x, float y, float z);
    default VertexConsumer addVertex(Object matrix, float x, float y, float z) { return this; }
    VertexConsumer setColor(int red, int green, int blue, int alpha);
    default VertexConsumer setColor(float red, float green, float blue, float alpha) { return this; }
    default VertexConsumer setColor(int color) { return this; }
    VertexConsumer setUv(float u, float v);
    VertexConsumer setUv1(int u, int v);
    VertexConsumer setUv2(int u, int v);
    default VertexConsumer setLight(int light) { return this; }
    default VertexConsumer setLight(int u, int v) { return this; }
    VertexConsumer setNormal(float x, float y, float z);
    
    default void putBulkData(Object pose, Object quad, float[] r, float g, float b, float a, float alpha, int[] light, int overlay, boolean flag) {}
    default void putBulkData(Object pose, Object quad, float r, float g, float b, float a, int light, int overlay) {}
    default void putBulkData(Object pose, Object quad, float[] r, float g, float b, int[] light, int overlay, boolean flag) {}
}

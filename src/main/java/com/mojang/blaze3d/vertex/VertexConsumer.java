package com.mojang.blaze3d.vertex;

public interface VertexConsumer {
    default VertexConsumer setLineWidth(float width) { return this; }
    default VertexConsumer addVertex(float x, float y, float z) { return this; }
    default VertexConsumer setColor(int r, int g, int b, int a) { return this; }
    default VertexConsumer setColor(int color) { return this; }
    default VertexConsumer setUv(float u, float v) { return this; }
    default VertexConsumer setUv1(int u, int v) { return this; }
    default VertexConsumer setUv2(int u, int v) { return this; }
    default VertexConsumer setNormal(float x, float y, float z) { return this; }
}

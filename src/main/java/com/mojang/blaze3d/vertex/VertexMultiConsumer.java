package com.mojang.blaze3d.vertex;

public class VertexMultiConsumer {
    public static VertexConsumer create(VertexConsumer first, VertexConsumer second) {
        return first;
    }
}

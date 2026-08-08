package com.mojang.blaze3d.systems;

import org.joml.Matrix4f;

public class RenderSystem {
    public static String apiDescription = "";

    public static float getShaderGameTime() { return 0.0f; }
    public static Matrix4f getTextureMatrix() { return new Matrix4f(); }
    public static void resetTextureMatrix() {}
    public static void assertOnRenderThread() {}
    public static void assertOnRenderThreadOrInit() {}
    public static void assertOnGameThreadOrInit() {}
    public static int getShaderTexture(Integer slot) { return 0; }
}

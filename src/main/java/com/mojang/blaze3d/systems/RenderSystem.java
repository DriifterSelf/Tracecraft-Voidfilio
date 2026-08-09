package com.mojang.blaze3d.systems;

import net.minecraft.client.renderer.CompiledShaderProgram;

public class RenderSystem {
    public static void setShaderTexture(int unit, Object location) {}
    public static CompiledShaderProgram getShader() { return new CompiledShaderProgram(); }
    public static void assertOnRenderThreadOrInit() {}
    public static boolean isOnRenderThreadOrInit() { return true; }
    public static void recordRenderCall(Runnable runnable) {}
}

package net.minecraft.client.renderer;

public class FogParameters {
    public static class FogShape {
        public int getIndex() { return 0; }
    }

    public float start() { return 0f; }
    public float end() { return 1f; }
    public float red() { return 0f; }
    public float green() { return 0f; }
    public float blue() { return 0f; }
    public float alpha() { return 1f; }
    public FogShape shape() { return new FogShape(); }
}

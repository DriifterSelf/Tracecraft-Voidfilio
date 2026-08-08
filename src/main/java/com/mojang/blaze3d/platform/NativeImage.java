package com.mojang.blaze3d.platform;

import java.io.InputStream;
import java.util.function.IntUnaryOperator;

public class NativeImage {
    public NativeImage() {}
    public NativeImage(int width, int height, boolean useStb) {}
    public NativeImage(Format format, int width, int height, boolean useStb) {}

    public static NativeImage read(InputStream stream) {
        return new NativeImage();
    }

    public int getWidth() { return 1; }
    public int getHeight() { return 1; }
    public void setPixel(int x, int y, int color) {}
    public int getPixel(int x, int y) { return 0; }
    public void close() {}
    public NativeImage mappedCopy(IntUnaryOperator mapper) { return new NativeImage(); }
    public void upload(int p1, int p2, int p3, int p4, int p5, int p6, int p7, boolean p8) {}
    public void upload(int level, int xOffset, int yOffset, boolean close) {}
    public void copyFrom(NativeImage other) {}
    public boolean copyFromFont(Object font, int index) { return true; }

    public Format format() { return Format.RGBA; }

    public enum Format {
        RGBA, RGB, LUMINANCE_ALPHA, LUMINANCE;
        public boolean hasAlpha() { return true; }
        public int alphaOffset() { return 24; }
        public int components() { return 4; }
        public InternalGlFormat getNativeImageInternalFormat() { return InternalGlFormat.RGBA; }
    }

    public enum InternalGlFormat {
        RGBA, RGB, RG, RED
    }
}

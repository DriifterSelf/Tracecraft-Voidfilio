package com.mojang.blaze3d.font;

public interface SheetGlyphInfo {
    default int getPixelWidth() { return 0; }
    default int getPixelHeight() { return 0; }
    default float getOversample() { return 1.0f; }
    default float getBearingTop() { return 0.0f; }
    default float getBearingLeft() { return 0.0f; }
    default void upload(int x, int y) {}
    default boolean isColored() { return false; }
}

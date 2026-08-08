package com.tracecraft.mixin_related.extensions.vanilla_resource_tracker;

import com.mojang.blaze3d.font.SheetGlyphInfo;

public interface IRenderableGlyphExt extends SheetGlyphInfo {
    void upload(int id, int x, int y);
    default float getLeft() { return 0; }
    default float getRight() { return 0; }
    default float getTop() { return 0; }
    default float getBottom() { return 0; }
}

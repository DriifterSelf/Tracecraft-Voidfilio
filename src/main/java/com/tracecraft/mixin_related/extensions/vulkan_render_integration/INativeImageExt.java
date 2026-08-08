package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import com.mojang.blaze3d.platform.NativeImage;

public interface INativeImageExt {

    void tracecraft$loadFromTextureImageWithoutUI(int level, boolean removeAlpha);

    NativeImage tracecraft$alignTo(NativeImage template);

    long tracecraft$getPointer();
}

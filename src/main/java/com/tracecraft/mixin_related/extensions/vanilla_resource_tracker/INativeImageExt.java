package com.tracecraft.mixin_related.extensions.vanilla_resource_tracker;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;

public interface INativeImageExt {

    int tracecraft$getTargetID();

    void tracecraft$setTargetID(int id);

    ResourceLocation tracecraft$getIdentifier();

    void tracecraft$setIdentifier(ResourceLocation id);

    NativeImage tracecraft$getSpecularNativeImage();

    void tracecraft$setSpecularNativeImage(NativeImage image);

    NativeImage tracecraft$getNormalNativeImage();

    void tracecraft$setNormalNativeImage(NativeImage image);

    NativeImage tracecraft$getFlagNativeImage();

    void tracecraft$setFlagNativeImage(NativeImage image);
}

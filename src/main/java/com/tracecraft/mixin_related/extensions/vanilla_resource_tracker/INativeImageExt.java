package com.tracecraft.mixin_related.extensions.vanilla_resource_tracker;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

public interface INativeImageExt {

    int tracecraft$getTargetID();

    void tracecraft$setTargetID(int id);

    Identifier tracecraft$getIdentifier();

    void tracecraft$setIdentifier(Identifier id);

    NativeImage tracecraft$getSpecularNativeImage();

    void tracecraft$setSpecularNativeImage(NativeImage image);

    NativeImage tracecraft$getNormalNativeImage();

    void tracecraft$setNormalNativeImage(NativeImage image);

    NativeImage tracecraft$getFlagNativeImage();

    void tracecraft$setFlagNativeImage(NativeImage image);
}

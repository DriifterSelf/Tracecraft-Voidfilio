package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import org.joml.Vector3f;

public interface ILightMapManagerExt {

    int tracecraft$getTextureId();

    float tracecraft$getAmbientLightFactor();

    float tracecraft$getSkyFactor();

    float tracecraft$getBlockFactor();

    boolean tracecraft$isUseBrightLightmap();

    Vector3f tracecraft$getSkyLightColor();

    float tracecraft$getNightVisionFactor();

    float tracecraft$getDarknessScale();

    float tracecraft$getDarkenWorldFactor();

    float tracecraft$getBrightnessFactor();
}

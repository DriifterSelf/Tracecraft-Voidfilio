package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import org.joml.Matrix4f;

public interface IGameRendererExt {

    Matrix4f tracecraft$getRotationMatrix();
}

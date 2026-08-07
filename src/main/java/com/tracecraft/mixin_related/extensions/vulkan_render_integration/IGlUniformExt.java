package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface IGlUniformExt {

    int tracecraft$getDataTypeValue();

    int tracecraft$getCountValue();

    IntBuffer tracecraft$getIntDataValue();

    FloatBuffer tracecraft$getFloatDataValue();
}

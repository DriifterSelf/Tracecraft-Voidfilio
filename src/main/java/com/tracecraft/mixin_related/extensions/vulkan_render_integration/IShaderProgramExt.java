package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.List;

public interface IShaderProgramExt {

    String tracecraft$getShaderName();

    void tracecraft$setShaderName(String shaderName);

    VertexFormat tracecraft$getVertexFormat();

    void tracecraft$setVertexFormat(VertexFormat vertexFormat);

    String tracecraft$getVertexSource();

    void tracecraft$setVertexSource(String vertexSource);

    String tracecraft$getFragmentSource();

    void tracecraft$setFragmentSource(String fragmentSource);

    List<String> tracecraft$getSamplerNamesValue();

    List<Uniform> tracecraft$getUniformsValue();

    Object2IntMap<String> tracecraft$getSamplerTexturesValue();
}

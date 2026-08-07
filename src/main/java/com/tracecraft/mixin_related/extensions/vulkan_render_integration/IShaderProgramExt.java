package com.tracecraft.mixin_related.extensions.vulkan_render_integration;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.List;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.VertexFormat;

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

    List<GlUniform> tracecraft$getUniformsValue();

    Object2IntMap<String> tracecraft$getSamplerTexturesValue();
}

package net.minecraft.client.renderer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RenderType {
    public String name = "";
    public static class CompositeRenderType extends RenderType {
        public RenderStateShard state = new RenderStateShard();
        public boolean sortOnUpload() { return false; }
    }

    public int bufferSize() { return 65536; }
    public boolean affectsCrumbling() { return false; }
    public boolean isOutline() { return false; }
    public Optional<RenderType> outline() { return Optional.empty(); }

    public static List<RenderType> chunkBufferLayers() { return Collections.emptyList(); }
    public static RenderType clouds() { return new RenderType(); }
    public static RenderType flatClouds() { return new RenderType(); }
    public static RenderType entitySolid(Object location) { return new RenderType(); }
    public static RenderType secondaryBlockOutline() { return new RenderType(); }
    public static RenderType lines() { return new RenderType(); }
}

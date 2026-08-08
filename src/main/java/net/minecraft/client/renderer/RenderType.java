package net.minecraft.client.renderer;

public class RenderType extends RenderStateShard {
    public final String name;

    public static class TextureState {
        public Object cutoutTexture() { return null; }
    }

    public static class CompositeState {
        public RenderStateShard.TransparencyStateShard transparencyState = RenderStateShard.NO_TRANSPARENCY;
        public TextureState textureState = new TextureState();
    }

    public boolean isOutline() {
        return false;
    }

    public java.util.Optional<RenderType> outline() {
        return java.util.Optional.empty();
    }

    public static class CompositeRenderType extends RenderType {
        public final CompositeState state = new CompositeState();

        public CompositeRenderType(String name) {
            super(name);
        }

        public boolean sortOnUpload() {
            return false;
        }
    }

    public RenderType(String name) {
        super(name, () -> {}, () -> {});
        this.name = name;
    }

    public String mode() {
        return "QUADS";
    }

    public boolean affectsCrumbling() {
        return false;
    }

    public static RenderType gui() {
        return new RenderType("gui");
    }

    public static RenderType guiTextured() {
        return new RenderType("gui_textured");
    }

    public static java.util.List<RenderType> chunkBufferLayers() {
        return java.util.Collections.emptyList();
    }

    public int bufferSize() {
        return 0;
    }

    public static RenderType lines() {
        return new RenderType("lines");
    }

    public static RenderType secondaryBlockOutline() {
        return new RenderType("secondary_block_outline");
    }

    public static RenderType entityCutout(net.minecraft.resources.ResourceLocation location) {
        return new RenderType("entity_cutout");
    }

    public static RenderType entitySolid(net.minecraft.resources.ResourceLocation location) {
        return new RenderType("entity_solid");
    }

    public static RenderType entityTranslucent(net.minecraft.resources.ResourceLocation location) {
        return new RenderType("entity_translucent");
    }
}

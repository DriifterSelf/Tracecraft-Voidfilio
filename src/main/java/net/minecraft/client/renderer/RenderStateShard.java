package net.minecraft.client.renderer;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public class RenderStateShard {
    public static Object NO_TRANSPARENCY = new Object();
    public static Object ADDITIVE_TRANSPARENCY = new Object();
    public static Object LIGHTNING_TRANSPARENCY = new Object();
    public static Object GLINT_TRANSPARENCY = new Object();
    public static Object CRUMBLING_TRANSPARENCY = new Object();
    public static Object OVERLAY_TRANSPARENCY = new Object();
    public static Object TRANSLUCENT_TRANSPARENCY = new Object();

    public Object transparencyState = new Object();

    public static class TextureStateShard {
        public Optional<ResourceLocation> cutoutTexture() { return Optional.empty(); }
    }

    public TextureStateShard textureState = new TextureStateShard();
}

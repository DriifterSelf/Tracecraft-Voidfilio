package net.minecraft.client.renderer;

public abstract class RenderStateShard {
    protected final String name;
    private final Runnable setupState;
    private final Runnable clearState;

    public static final TransparencyStateShard NO_TRANSPARENCY = new TransparencyStateShard("no_transparency");
    public static final TransparencyStateShard ADDITIVE_TRANSPARENCY = new TransparencyStateShard("additive_transparency");
    public static final TransparencyStateShard LIGHTNING_TRANSPARENCY = new TransparencyStateShard("lightning_transparency");
    public static final TransparencyStateShard GLINT_TRANSPARENCY = new TransparencyStateShard("glint_transparency");
    public static final TransparencyStateShard CRUMBLING_TRANSPARENCY = new TransparencyStateShard("crumbling_transparency");
    public static final TransparencyStateShard OVERLAY_TRANSPARENCY = new TransparencyStateShard("overlay_transparency");
    public static final TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new TransparencyStateShard("translucent_transparency");

    public static class TransparencyStateShard extends RenderStateShard {
        public TransparencyStateShard(String name) {
            super(name, () -> {}, () -> {});
        }
    }

    public static void setupGlintTexturing(float speed) {}

    public RenderStateShard(String name, Runnable setupState, Runnable clearState) {
        this.name = name;
        this.setupState = setupState;
        this.clearState = clearState;
    }

    public void setupRenderState() {
        setupState.run();
    }

    public void clearRenderState() {
        clearState.run();
    }
}

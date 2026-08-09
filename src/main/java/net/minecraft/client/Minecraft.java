package net.minecraft.client;

import com.mojang.blaze3d.platform.Window;
import java.io.File;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureManager;

public class Minecraft {
    private static final Minecraft INSTANCE = new Minecraft();
    public static Minecraft getInstance() { return INSTANCE; }
    public Window getWindow() { return new Window(); }
    public Options options = new Options();
    public ClientLevel level = new ClientLevel();
    public LocalPlayer player = new LocalPlayer();
    public Object hitResult = null;
    public ParticleEngine particleEngine = new ParticleEngine();
    public File gameDirectory = new File(".");
    public Font font = new Font();
    public void setScreen(Object screen) {}
    public TextureManager getTextureManager() { return new TextureManager(); }
    public boolean shouldEntityAppearGlowing(Object entity) { return false; }
    public Object getCameraEntity() { return null; }

    public static class FramerateLimitTracker {
        public void setFramerateLimit(int limit) {}
        public void setFramerateLimit(Integer limit) {}
    }

    public FramerateLimitTracker getFramerateLimitTracker() { return new FramerateLimitTracker(); }

    public static class GameMode {
        public Object getPlayerMode() { return null; }
    }

    public GameMode gameMode = new GameMode();

    public static class EntityRenderDispatcher {
        public int getPackedLightCoords(Object entity, float delta) { return 0; }
    }

    public EntityRenderDispatcher getEntityRenderDispatcher() { return new EntityRenderDispatcher(); }

    public static class GameRenderer {
        public float getDepthFar() { return 100.0f; }
    }

    public GameRenderer gameRenderer = new GameRenderer();
}

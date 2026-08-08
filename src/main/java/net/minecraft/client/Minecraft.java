package net.minecraft.client;

import java.io.File;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.gui.Font;

public class Minecraft {
    private static final Minecraft INSTANCE = new Minecraft();
    public File gameDirectory = new File(".");
    public Options options = new Options();
    public ClientLevel level = new ClientLevel();
    public GameMode gameMode = new GameMode();
    public LocalPlayer player = new LocalPlayer();
    public ParticleEngine particleEngine = new ParticleEngine();
    public GameRenderer gameRenderer = new GameRenderer();
    public Font font = new Font();
    public Object hitResult;

    public static class GameRenderer {
        public float getDepthFar() { return 1000f; }
    }

    public static class GameMode {
        public Object getPlayerMode() { return null; }
    }

    public static class Options {
        public String languageCode = "en_us";
        public boolean hideGui = false;
        public CameraType getCameraType() { return new CameraType(); }
        public int getEffectiveRenderDistance() { return 12; }
        public OptionSupplier highContrastBlockOutline() { return new OptionSupplier(); }
    }

    public static class OptionSupplier {
        public Boolean get() { return false; }
    }

    public static class CameraType {
        public boolean isFirstPerson() { return true; }
    }

    public static Minecraft getInstance() {
        return INSTANCE;
    }

    public Window getWindow() {
        return new Window();
    }

    public TextureManager getTextureManager() {
        return new TextureManager();
    }

    public EntityRenderDispatcher getEntityRenderDispatcher() {
        return new EntityRenderDispatcher();
    }

    public Object getCameraEntity() { return null; }
    public boolean shouldEntityAppearGlowing(Object entity) { return false; }
    public void setScreen(Object screen) {}
    public Object getLanguageManager() { return null; }
}

package net.minecraft.client;

import net.minecraft.client.OptionInstance;

public class Options {
    public static final String CATEGORY_GAMEPLAY = "controls.keybinds.gameplay";
    public static final String CATEGORY_WINDOW = "options.videoTitle";
    public static final String CATEGORY_TERRAIN = "options.qualityTitle";
    public static final String CATEGORY_PIPELINE = "options.performanceTitle";

    public boolean hideGui = false;

    public OptionInstance<?> mipmapLevels() { return null; }
    public OptionInstance<?> fullscreen() { return null; }
    public OptionInstance<?> renderDistance() { return null; }
    public OptionInstance<?> simulationDistance() { return null; }
    public OptionInstance<?> framerateLimit() { return null; }
    public OptionInstance<Boolean> highContrastBlockOutline() { return null; }
    public OptionInstance<?> graphicsMode() { return null; }
    public OptionInstance<?> guiScale() { return null; }
    public OptionInstance<?> attackIndicator() { return null; }
    public OptionInstance<?> gamma() { return null; }
    public OptionInstance<?> cloudStatus() { return null; }
    public OptionInstance<?> particles() { return null; }
    public OptionInstance<?> screenEffectScale() { return null; }
    public OptionInstance<?> entityDistanceScaling() { return null; }
    public OptionInstance<?> fovEffectScale() { return null; }
    public OptionInstance<?> showAutosaveIndicator() { return null; }
    public OptionInstance<?> glintSpeed() { return null; }
    public OptionInstance<?> glintStrength() { return null; }
    public OptionInstance<?> menuBackgroundBlurriness() { return null; }
    public OptionInstance<?> bobView() { return null; }
    public OptionInstance<?> biomeBlendRadius() { return null; }
    public float getMenuBackgroundBlurriness() { return 0.0F; }
    public CameraType getCameraType() { return CameraType.FIRST_PERSON; }
    public int getEffectiveRenderDistance() { return 12; }

    public enum CameraType {
        FIRST_PERSON, THIRD_PERSON_BACK, THIRD_PERSON_FRONT;
        public boolean isFirstPerson() { return this == FIRST_PERSON; }
    }
}

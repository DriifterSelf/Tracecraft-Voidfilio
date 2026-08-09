package com.mojang.blaze3d.platform;

import java.util.Optional;

public class Window {
    public int getWidth() { return 1920; }
    public int getHeight() { return 1080; }
    public int getScreenWidth() { return 1920; }
    public int getScreenHeight() { return 1080; }
    public long getWindow() { return 1L; }
    public Monitor findBestMonitor() { return new Monitor(); }
    public Optional<VideoMode> getPreferredFullscreenVideoMode() { return Optional.empty(); }
    public void setPreferredFullscreenVideoMode(Object mode) {}
}

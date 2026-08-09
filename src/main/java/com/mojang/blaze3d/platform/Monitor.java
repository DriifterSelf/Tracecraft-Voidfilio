package com.mojang.blaze3d.platform;

public class Monitor {
    public int getVideoModeIndex(Object mode) { return 0; }
    public int getModeCount() { return 1; }
    public VideoMode getMode(int index) { return new VideoMode(); }
}

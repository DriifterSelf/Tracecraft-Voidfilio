package com.tracecraft.client.pipeline;
public enum Presets {
    ULTIMATE_RT_FSR4("ULTIMATE_RT_FSR4", "render_pipeline.preset.ultimate_rt_fsr4");

    public final String name;
    public final String key;

    Presets(String name, String key) {
        this.name = name;
        this.key = key;
    }
}

package com.tracecraft;

import com.tracecraft.mixin_related.MixinPlugin;
import net.fabricmc.api.ModInitializer;

public class Tracecraft implements ModInitializer {

    public static final String MOD_ID = "tracecraft";

    @Override
    public void onInitialize() {
        MixinPlugin.log("Tracecraft ModInitializer onInitialize() invocado exitosamente.");
    }
}

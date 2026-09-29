package com.tracecraft.mixins.vulkan_options;

import static net.minecraft.client.InactivityFpsLimit.AFK;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import com.mojang.serialization.Codec;
import com.tracecraft.client.gui.PotentialValuesBasedCallbacksNoValue;
import com.tracecraft.client.gui.RenderPipelineScreen;
import com.tracecraft.client.option.Options;
import com.tracecraft.client.util.CategoryVideoOptionEntry;
import java.util.Optional;
import net.minecraft.client.InactivityFpsLimit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public class VideoOptionsScreenMixins extends GameOptionsScreenMixins {

    @Unique
    private static Component genericValueLabel(Component optionText, Component valueText) {
        return Component.translatable("options.generic_value", optionText, valueText);
    }

    @Unique
    private static final Component INACTIVITY_FPS_LIMIT_MINIMIZED_TOOLTIP = Component.translatable(
        "options.inactivityFpsLimit.minimized.tooltip");
    @Unique
    private static final Component INACTIVITY_FPS_LIMIT_AFK_TOOLTIP = Component.translatable(
        "options.inactivityFpsLimit.afk.tooltip");

    @Unique
    private static final PotentialValuesBasedCallbacksNoValue<Boolean> BOOLEAN_NO_KEY = new PotentialValuesBasedCallbacksNoValue<>(
        ImmutableList.of(Boolean.TRUE, Boolean.FALSE), Codec.BOOL
    );

    @Inject(method = "addOptions()V", at = @At(value = "HEAD"), cancellable = true)
    public void redirectAddOptions(CallbackInfo ci) {
        OptionInstance<Integer> maxFps =
            new OptionInstance<Integer>("options.framerateLimit",
                OptionInstance.noTooltip(),
                (optionText, value) -> value == 260 ?
                    genericValueLabel(optionText, Component.translatable("options.framerateLimit.max"))
                    :
                        genericValueLabel(optionText,
                            Component.translatable("options.framerate", value)),
                new OptionInstance.IntRange(1, 26).xmap(
                    value -> value * 10, value -> value / 10),
                Codec.intRange(10, 260),
                Options.maxFps,
                value -> {
                    Minecraft.getInstance()
                        .getFramerateLimitTracker()
                        .setFramerateLimit(value);
                    Options.setMaxFps(value, true);
                });

        Window window = Minecraft.getInstance().getWindow();
        Monitor monitor = window.findBestMonitor();
        int j;
        if (monitor == null) {
            j = -1;
        } else {
            Optional<VideoMode> optional = window.getPreferredFullscreenVideoMode();
            j = optional.map(monitor::getVideoModeIndex).orElse(-1);
        }

        OptionInstance<Integer> fullScreenResolutionOption =
            new OptionInstance<Integer>("options.fullscreen.resolution", OptionInstance.noTooltip(),
                (optionText, value) -> {
                    if (monitor == null) {
                        return Component.translatable("options.fullscreen.unavailable");
                    } else if (value == -1) {
                        return genericValueLabel(optionText,
                            Component.translatable("options.fullscreen.current"));
                    } else {
                        VideoMode videoMode = monitor.getMode(value);
                        return genericValueLabel(optionText,
                            Component.translatable("options.fullscreen.entry",
                                videoMode.getWidth(),
                                videoMode.getHeight(),
                                videoMode.getRefreshRate(),
                                videoMode.getRedBits() + videoMode.getGreenBits() +
                                    videoMode.getBlueBits()));
                    }
                }, new OptionInstance.IntRange(-1,
                monitor != null ? monitor.getModeCount() - 1 : -1), j, value -> {
                if (monitor != null) {
                    window.setPreferredFullscreenVideoMode(
                        value == -1 ? Optional.empty() : Optional.of(monitor.getMode(value)));
                }
            });

        OptionInstance<InactivityFpsLimit> inactivityFpsLimit = OptionInstance.createEnum(
            "options.inactivityFpsLimit",
            option -> switch (option) {
                case MINIMIZED -> Tooltip.create(INACTIVITY_FPS_LIMIT_MINIMIZED_TOOLTIP);
                case AFK -> Tooltip.create(INACTIVITY_FPS_LIMIT_AFK_TOOLTIP);
            },
            inactivityLimit -> {
                Options.setInactivityFpsLimit(
                    inactivityLimit == AFK ? 30 : 9, true);
            });

        OptionInstance<Boolean> enableVsync = OptionInstance.createBoolean("options.vsync", Options.vsync,
            value -> {
                if (Minecraft.getInstance().getWindow() != null) {
                    Options.setVsync(value, true);
                }
            });

        OptionInstance<Integer> chunkBuildingBatchSize =
            new OptionInstance<Integer>(Options.CHUNK_BUILDING_BATCH_SIZE_KEY,
                OptionInstance.noTooltip(),
                (optionText, value) -> genericValueLabel(optionText,
                    Component.literal(Integer.toString(value))),
                new OptionInstance.IntRange(1, 32),
                Codec.intRange(1, 32),
                Options.chunkBuildingBatchSize,
                value -> {
                    Options.setChunkBuildingBatchSize(value, true);
                });

        OptionInstance<Integer> chunkBuildingTotalBatches =
            new OptionInstance<Integer>(Options.CHUNK_BUILDING_TOTAL_BATCHES_KEY,
                OptionInstance.noTooltip(),
                (optionText, value) -> genericValueLabel(optionText,
                    Component.literal(Integer.toString(value))),
                new OptionInstance.IntRange(1, 32),
                Codec.intRange(1, 32),
                Options.chunkBuildingTotalBatches,
                value -> {
                    Options.setChunkBuildingTotalBatches(value, true);
                });

        OptionInstance<Integer> chunkBuildingThreads =
            new OptionInstance<Integer>(Options.CHUNK_BUILDING_THREADS_KEY, OptionInstance.noTooltip(),
                (optionText, value) -> genericValueLabel(optionText,
                    Component.literal(Integer.toString(value))),
                new OptionInstance.IntRange(1,
                    Options.getMaxChunkBuildingThreads()),
                Codec.intRange(1, Options.getMaxChunkBuildingThreads()),
                Options.chunkBuildingThreads,
                value -> Options.setChunkBuildingThreads(value, true));

        OptionInstance<Boolean> collectChunkEmission = OptionInstance.createBoolean(
            Options.COLLECT_CHUNK_EMISSION_KEY,
            Options.collectChunkEmission,
            value -> Options.setCollectChunkEmission(value, true));

        OptionInstance<Boolean> pipelineSettings = new OptionInstance<Boolean>(Options.PIPELINE_SETUP_KEY,
            OptionInstance.noTooltip(),
            (optionText, value) -> optionText,
            BOOLEAN_NO_KEY,
            false,
            value -> {
                Minecraft.getInstance()
                    .setScreen(new RenderPipelineScreen((VideoSettingsScreen) (Object) this));
            });

        // Adding categories and options
        this.list.addEntry(
            new CategoryVideoOptionEntry(Component.translatable(Options.CATEGORY_GAMEPLAY), list));
        OptionInstance<?>[] optionsGameplay = new OptionInstance<?>[]{
            options.graphicsMode(),
            options.renderDistance(),
            options.simulationDistance(),
            options.guiScale(),
            options.attackIndicator(),
            options.gamma(),
            options.cloudStatus(),
            options.particles(),
            options.screenEffectScale(),
            options.entityDistanceScaling(),
            options.fovEffectScale(),
            options.showAutosaveIndicator(),
            options.glintSpeed(),
            options.glintStrength(),
            options.menuBackgroundBlurriness(),
            options.bobView(),
        };
        this.list.addBig(options.biomeBlendRadius());
        this.list.addBig(options.mipmapLevels());
        this.list.addSmall(optionsGameplay);

        this.list.addEntry(
            new CategoryVideoOptionEntry(Component.translatable(Options.CATEGORY_WINDOW), list));
        OptionInstance<?>[] optionsWindow = new OptionInstance<?>[]{
            maxFps,
            inactivityFpsLimit,
            enableVsync,
            options.fullscreen(),
        };
        this.list.addSmall(optionsWindow);
        this.list.addBig(fullScreenResolutionOption);

        this.list.addEntry(
            new CategoryVideoOptionEntry(Component.translatable(Options.CATEGORY_TERRAIN), list));
        this.list.addBig(chunkBuildingBatchSize);
        this.list.addBig(chunkBuildingTotalBatches);
        this.list.addBig(chunkBuildingThreads);
        this.list.addBig(collectChunkEmission);

        this.list.addEntry(
            new CategoryVideoOptionEntry(Component.translatable(Options.CATEGORY_PIPELINE), list));
        this.list.addBig(pipelineSettings);

        ci.cancel();
    }
}

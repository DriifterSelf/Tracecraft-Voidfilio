package com.tracecraft.client.gui;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.OptionInstance.TooltipSupplier;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public record PotentialValuesBasedCallbacksNoValue<T>(List<T> values, Codec<T> codec) implements
    OptionInstance.CycleableValueSet<T> {

    @Override
    public Optional<T> validateValue(T value) {
        return this.values.contains(value) ? Optional.of(value) : Optional.empty();
    }

    @Override
    public CycleButton.ValueListSupplier<T> valueListSupplier() {
        return CycleButton.ValueListSupplier.create(this.values);
    }

    @Override
    public Function<OptionInstance<T>, AbstractWidget> createButton(
        TooltipSupplier<T> tooltipFactory, Options gameOptions, int x, int y, int width,
        Consumer<T> changeCallback) {
        return option -> CycleButton.builder((T val) -> Component.literal(String.valueOf(val)))
            .withValues(this.valueListSupplier())
            .withTooltip(tooltipFactory)
            .withInitialValue(option.get())
            .displayOnlyValue()
            .create(x, y, width, 20, Component.literal("Option"), (button, value) -> {
                gameOptions.save();
                changeCallback.accept(value);
            });
    }

    public Function<OptionInstance<T>, AbstractWidget> createButton(
        Options gameOptions, int x, int y, int width, Consumer<T> changeCallback) {
        return createButton(option -> null, gameOptions, x, y, width, changeCallback);
    }
}

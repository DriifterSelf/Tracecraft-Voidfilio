package net.minecraft.client;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public class OptionInstance<T> {
    public interface TooltipSupplier<T> {}

    public interface CycleableValueSet<T> {}

    public interface CaptionBasedToString<T> {
        Object toString(net.minecraft.network.chat.Component caption, T value);
    }

    public static <T> TooltipSupplier<T> noTooltip() { return null; }
    public static <T> Object forOptionEnum() { return null; }
    public static OptionInstance<Boolean> createBoolean(String caption, boolean defaultValue, Consumer<Boolean> onValueUpdate) {
        return new OptionInstance<>(caption, null, null, null, defaultValue, onValueUpdate);
    }

    public OptionInstance(String caption, TooltipSupplier<T> tooltip, Object captionValueConsumer, Object valueSet, T defaultValue, Consumer<T> onValueUpdate) {}
    public OptionInstance(String caption, TooltipSupplier<T> tooltip, CaptionBasedToString<T> captionValueConsumer, Object valueSet, T defaultValue, Consumer<T> onValueUpdate) {}
    public OptionInstance(String caption, TooltipSupplier<T> tooltip, CaptionBasedToString<T> captionValueConsumer, Object valueSet, Object codec, T defaultValue, Consumer<T> onValueUpdate) {}
    public OptionInstance(String caption, TooltipSupplier<T> tooltip, Object captionValueConsumer, Object valueSet, Object codec, T defaultValue, Consumer<T> onValueUpdate) {}

    public T get() { return null; }

    public static class Enum<T> {
        public Enum(List<T> values, Object codec) {}
    }

    public static class IntRange {
        public IntRange(int min, int max) {}
        public <R> Object xmap(IntFunction<? extends R> from, ToIntFunction<? super R> to) { return this; }
        public <R> Object xmap(IntFunction<? extends R> from, ToIntFunction<? super R> to, boolean flag) { return this; }
        public Object xmap(Object value, Object value2) { return this; }
        public Object xmap(Object value, Object value2, boolean flag) { return this; }
    }
}

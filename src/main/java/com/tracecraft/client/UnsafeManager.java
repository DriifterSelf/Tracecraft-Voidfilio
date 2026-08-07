package com.tracecraft.client;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public enum UnsafeManager {
    INSTANCE;

    private final Unsafe unsafe;

    UnsafeManager() {
        this.unsafe = initUnsafe();
    }

    private static Unsafe initUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Throwable t) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T allocateInstance(Class<T> cls) {
        if (unsafe == null) {
            throw new UnsupportedOperationException("Unsafe no disponible en este entorno JVM.");
        }
        try {
            return (T) unsafe.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public Unsafe raw() {
        return unsafe;
    }
}

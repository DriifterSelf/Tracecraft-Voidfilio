package com.tracecraft.mixin_related;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinPlugin implements IMixinConfigPlugin {

    public static boolean ENABLED = true;
    private static File logFile;

    static {
        initEarlyLogging();
    }

    private static synchronized void initEarlyLogging() {
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            File modsDir = new File(currentDir, "mods");
            if (!modsDir.exists()) {
                modsDir.mkdirs();
            }
            logFile = new File(modsDir, "tracecraft_debug.log");

            // Escribir cabecera de depuración en la carpeta mods
            log("================================================================================");
            log("TRACECRAFT DEBUGER INICIADO EN CARPETA MODS: " + logFile.getAbsolutePath());
            log("Fecha/Hora local: " + LocalDateTime.now());
            log("Java Versión: " + System.getProperty("java.version") + " (" + System.getProperty("java.vendor") + ")");
            log("JVM Runtime: " + System.getProperty("java.runtime.name") + " " + System.getProperty("java.runtime.version"));
            log("Sistema Operativo: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " v" + System.getProperty("os.version"));
            log("Directorio de Trabajo (user.dir): " + currentDir.getAbsolutePath());
            log("================================================================================");

            // Capturar cualquier excepción no controlada en cualquier hilo de la JVM
            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
                logError("CRASH DETECTADO EN HILO [" + thread.getName() + "]", throwable);
            });
        } catch (Throwable t) {
            System.err.println("[Tracecraft] Error al crear logger en carpeta mods: " + t.getMessage());
        }
    }

    public static synchronized void log(String message) {
        if (logFile == null) return;
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), StandardCharsets.UTF_8))) {
            writer.println("[" + LocalDateTime.now() + "] [INFO] " + message);
            writer.flush();
        } catch (Throwable ignored) {
        }
    }

    public static synchronized void logError(String context, Throwable t) {
        if (logFile == null) return;
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile, true), StandardCharsets.UTF_8))) {
            writer.println("[" + LocalDateTime.now() + "] [ERROR] " + context);
            if (t != null) {
                StringWriter sw = new StringWriter();
                t.printStackTrace(new PrintWriter(sw));
                writer.println(sw.toString());
            }
            writer.flush();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
        log("Cargando paquete Mixin: " + mixinPackage);
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        try {
            log("Evaluando Mixin: " + mixinClassName + " -> Objetivo: " + targetClassName);
        } catch (Throwable ignored) {
        }
        return ENABLED;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName,
        IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName,
        IMixinInfo mixinInfo) {
        try {
            log("Mixin aplicado con éxito: " + mixinClassName + " en " + targetClassName);
        } catch (Throwable ignored) {
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }
}

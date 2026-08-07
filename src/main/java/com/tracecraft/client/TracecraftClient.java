package com.tracecraft.client;

import com.mojang.logging.LogUtils;
import com.tracecraft.client.option.Options;
import com.tracecraft.client.pipeline.Pipeline;
import com.tracecraft.client.proxy.vulkan.RendererProxy;
import com.tracecraft.mixin_related.MixinPlugin;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;

public class TracecraftClient implements ClientModInitializer {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static Path tracecraftDir;

    @Override
    public void onInitializeClient() {
        MixinPlugin.log("Inicializando Tracecraft Client Mod...");
        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            Path mcBaseDir = mc.runDirectory != null ? mc.runDirectory.toPath() : Paths.get(System.getProperty("user.dir"));
            tracecraftDir = mcBaseDir.resolve("tracecraft");
            try {
                Files.createDirectories(tracecraftDir);
            } catch (IOException e) {
                MixinPlugin.logError("No se pudo crear el directorio de Tracecraft: " + tracecraftDir, e);
            }

            // core lib & DLL native setup
            String osName = System.getProperty("os.name");
            Path dllTargetPath = null;
            if (osName.toLowerCase().contains("windows")) {
                Path libTargetPath = tracecraftDir.resolve("core.lib");
                copyFileFromResource(libTargetPath, Path.of("core.lib"));

                dllTargetPath = tracecraftDir.resolve("core.dll");
                copyFileFromResource(dllTargetPath, Path.of("core.dll"));

                Path xessPath = tracecraftDir.resolve("libxess.dll");
                Path xessDx11Path = tracecraftDir.resolve("libxess_dx11.dll");
                Path xessFgPath = tracecraftDir.resolve("libxess_fg.dll");
                copyOptionalFileFromResource(xessPath, Path.of("libxess.dll"));
                copyOptionalFileFromResource(xessDx11Path, Path.of("libxess_dx11.dll"));
                copyOptionalFileFromResource(xessFgPath, Path.of("libxess_fg.dll"));

                loadOptionalLibrary(xessPath);
            } else if (osName.toLowerCase().contains("linux")) {
                Path soTargetPath = tracecraftDir.resolve("libcore.so");
                copyFileFromResource(soTargetPath, Path.of("libcore.so"));
                dllTargetPath = soTargetPath;
            } else {
                MixinPlugin.log("Sistema operativo no soportado nativamente: " + osName);
            }

            // Shaders y módulos
            Path shaderTargetPath = tracecraftDir.resolve("shaders");
            copyFolderFromResource(shaderTargetPath, Path.of("shaders"));

            Path moduleTargetPath = tracecraftDir.resolve("modules");
            copyFolderFromResource(moduleTargetPath, Path.of("modules"));

            // Inicialización protegida de bibliotecas nativas
            if (dllTargetPath != null && Files.exists(dllTargetPath)) {
                try {
                    System.load(dllTargetPath.toAbsolutePath().toString());
                    MixinPlugin.log("Biblioteca nativa C++ Vulkan cargada exitosamente: " + dllTargetPath);
                    try {
                        RendererProxy.initFolderPath(tracecraftDir.toAbsolutePath().toString());
                        Pipeline.initFolderPath(tracecraftDir);
                        Options.readOptions();
                        Pipeline.reloadAllModuleEntries();
                    } catch (Throwable t) {
                        MixinPlugin.logError("Error al configurar RendererProxy/Pipeline", t);
                    }
                } catch (UnsatisfiedLinkError e) {
                    MixinPlugin.logError("Dependencia nativa no encontrada al cargar " + dllTargetPath + ". Continuando en modo compatible.", e);
                } catch (Throwable t) {
                    MixinPlugin.logError("No se pudieron inicializar llamadas nativas C++ Vulkan en: " + dllTargetPath, t);
                }
            } else {
                MixinPlugin.log("Tracecraft cliente listo. Las bibliotecas nativas Vulkan se inicializarán al detectar hardware AMD Radeon RX 9000 Series Flagship.");
            }
        } catch (Throwable t) {
            MixinPlugin.logError("Excepción capturada durante onInitializeClient()", t);
        }
    }

    public void copyFileFromResource(Path targetPath, Path resourcePath) {
        String resPathStr = toResourcePath(resourcePath);
        try (InputStream is = getClass().getResourceAsStream(resPathStr)) {
            if (is == null) {
                MixinPlugin.log("Recurso opcional no encontrado en JAR: " + resPathStr);
                return;
            }

            Files.createDirectories(targetPath.getParent());
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable e) {
            MixinPlugin.logError("Error al copiar recurso " + resPathStr, e);
        }
    }

    public void copyOptionalFileFromResource(Path targetPath, Path resourcePath) {
        copyFileFromResource(targetPath, resourcePath);
    }

    public void loadOptionalLibrary(Path path) {
        if (Files.exists(path)) {
            try {
                System.load(path.toAbsolutePath().toString());
                MixinPlugin.log("Biblioteca opcional cargada: " + path);
            } catch (Throwable e) {
                MixinPlugin.logError("Error al cargar biblioteca opcional " + path, e);
            }
        }
    }

    public String toResourcePath(Path path) {
        String joined = StreamSupport.stream(path.spliterator(), false).map(Object::toString)
            .collect(Collectors.joining("/"));
        return "/" + joined;
    }

    public void copyFolderFromResource(Path targetPath, Path resourcePath) {
        String resourcePathStr = toResourcePath(resourcePath);
        URL url = getClass().getResource(resourcePathStr);

        if (url == null) {
            MixinPlugin.log("Carpeta de recursos opcional no encontrada en JAR: " + resourcePathStr);
            return;
        }

        try {
            URI uri = url.toURI();

            if ("jar".equals(uri.getScheme())) {
                JarURLConnection conn = (JarURLConnection) url.openConnection();
                URI jarFileUri = conn.getJarFileURL().toURI();
                URI jarFsUri = URI.create("jar:" + jarFileUri);

                FileSystem fs = null;
                boolean created = false;
                try {
                    try {
                        fs = FileSystems.getFileSystem(jarFsUri);
                    } catch (FileSystemNotFoundException e) {
                        fs = FileSystems.newFileSystem(jarFsUri, Collections.emptyMap());
                        created = true;
                    }

                    Path root = fs.getPath(resourcePathStr);
                    walkAndCopy(root, targetPath, resourcePath);
                } finally {
                    if (created) {
                        try {
                            fs.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            } else {
                Path root = Paths.get(uri);
                walkAndCopy(root, targetPath, resourcePath);
            }
        } catch (Throwable e) {
            MixinPlugin.logError("Error al copiar carpeta de recursos " + resourcePathStr, e);
        }
    }

    private void walkAndCopy(Path walkRoot, Path targetRoot, Path baseResourcePath)
        throws IOException {
        try (Stream<Path> stream = Files.walk(walkRoot)) {
            stream.filter(Files::isRegularFile).forEach(source -> {
                String relativePathStr = walkRoot.relativize(source).toString();
                Path targetFile = targetRoot.resolve(relativePathStr);
                Path childResourcePath = baseResourcePath.resolve(relativePathStr);
                copyFileFromResource(targetFile, childResourcePath);
            });
        }
    }
}

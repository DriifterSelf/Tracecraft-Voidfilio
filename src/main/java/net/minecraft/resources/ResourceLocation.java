package net.minecraft.resources;

public class ResourceLocation {
    private final String namespace;
    private final String path;

    public ResourceLocation(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public ResourceLocation(String location) {
        String[] parts = location.split(":", 2);
        if (parts.length > 1) {
            this.namespace = parts[0];
            this.path = parts[1];
        } else {
            this.namespace = "minecraft";
            this.path = parts[0];
        }
    }

    public static ResourceLocation parse(String location) {
        return new ResourceLocation(location);
    }

    public static ResourceLocation fromNamespaceAndPath(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation withDefaultNamespace(String path) {
        return new ResourceLocation("minecraft", path);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public ResourceLocation withPrefix(String prefix) {
        return new ResourceLocation(namespace, prefix + path);
    }

    public ResourceLocation withSuffix(String suffix) {
        return new ResourceLocation(namespace, path + suffix);
    }

    public ResourceLocation withPath(String newPath) {
        return new ResourceLocation(namespace, newPath);
    }
}

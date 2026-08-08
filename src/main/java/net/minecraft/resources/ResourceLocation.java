package net.minecraft.resources;

public class ResourceLocation {
    private final String namespace;
    private final String path;

    public ResourceLocation(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public static ResourceLocation parse(String location) {
        String[] split = location.split(":", 2);
        if (split.length == 2) {
            return new ResourceLocation(split[0], split[1]);
        }
        return new ResourceLocation("minecraft", location);
    }

    public static ResourceLocation withDefaultNamespace(String path) {
        return new ResourceLocation("minecraft", path);
    }

    public static ResourceLocation fromNamespaceAndPath(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public ResourceLocation withPath(String newPath) {
        return new ResourceLocation(this.namespace, newPath);
    }

    public String getNamespace() { return namespace; }
    public String getPath() { return path; }
}

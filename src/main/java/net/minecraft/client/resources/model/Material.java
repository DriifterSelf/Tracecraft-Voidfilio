package net.minecraft.client.resources.model;

import net.minecraft.resources.ResourceLocation;

public class Material {
    private final ResourceLocation atlasLocation;
    private final ResourceLocation texture;

    public Material(ResourceLocation atlasLocation, ResourceLocation texture) {
        this.atlasLocation = atlasLocation;
        this.texture = texture;
    }

    public ResourceLocation atlasLocation() {
        return atlasLocation;
    }

    public ResourceLocation texture() {
        return texture;
    }
}

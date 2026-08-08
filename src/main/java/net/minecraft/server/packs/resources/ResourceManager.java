package net.minecraft.server.packs.resources;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;

public interface ResourceManager {
    default Optional<Resource> getResource(ResourceLocation location) {
        return Optional.empty();
    }

    default Map<ResourceLocation, Resource> listResources(String path, Predicate<ResourceLocation> filter) {
        return new HashMap<>();
    }
}

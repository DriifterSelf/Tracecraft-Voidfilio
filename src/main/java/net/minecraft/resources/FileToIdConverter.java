package net.minecraft.resources;

import java.util.Collections;
import java.util.Map;
import net.minecraft.server.packs.resources.Resource;

public class FileToIdConverter {
    public FileToIdConverter(String prefix, String extension) {}
    public Map<ResourceLocation, Resource> listMatchingResources(Object resourceManager) { return Collections.emptyMap(); }
    public ResourceLocation fileToId(ResourceLocation location) { return location; }
}

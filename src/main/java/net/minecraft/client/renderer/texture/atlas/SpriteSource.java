package net.minecraft.client.renderer.texture.atlas;

import net.minecraft.resources.ResourceLocation;

public interface SpriteSource {
    class FileConverter {
        public ResourceLocation idToFile(ResourceLocation id) { return id; }
    }
    FileConverter TEXTURE_ID_CONVERTER = new FileConverter();
}

package com.tracecraft.mixins.vulkan_render_integration;

import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IOverlayTextureExt;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OverlayTexture.class)
public class OverlayTextureMixins implements IOverlayTextureExt {

    @Final
    @Shadow
    private NativeImageBackedTexture texture;

    @Override
    public AbstractTexture tracecraft$getTexture() {
        return texture;
    }
}

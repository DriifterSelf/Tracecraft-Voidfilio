package com.tracecraft.mixins.vulkan_render_integration;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.NativeImage;
import com.tracecraft.mixin_related.extensions.vanilla_resource_tracker.INativeImageExt;
import net.minecraft.client.renderer.texture.MipmapGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MipmapGenerator.class)
public class MipmapHelperMixins {

    @Inject(method = "generateMipLevels([Lcom/mojang/blaze3d/platform/NativeImage;I)[Lcom/mojang/blaze3d/platform/NativeImage;", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/NativeImage;getWidth()I", ordinal = 1))
    private static void addIdentifier(NativeImage[] originals, int mipmap,
        CallbackInfoReturnable<NativeImage[]> cir, @Local(ordinal = 0) NativeImage nativeImage,
        @Local(ordinal = 1) NativeImage nativeImage2) {

        ((INativeImageExt) (Object) nativeImage2).tracecraft$setIdentifier(
            ((INativeImageExt) (Object) nativeImage).tracecraft$getIdentifier());
    }
}

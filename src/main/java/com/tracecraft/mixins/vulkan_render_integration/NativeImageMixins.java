package com.tracecraft.mixins.vulkan_render_integration;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tracecraft.client.proxy.vulkan.RendererProxy;
import com.tracecraft.client.proxy.vulkan.TextureProxy;
import com.tracecraft.client.texture.AuxiliaryTextures;
import com.tracecraft.mixin_related.extensions.vanilla_resource_tracker.INativeImageExt;
import java.util.function.IntUnaryOperator;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NativeImage.class)
public abstract class NativeImageMixins implements
    com.tracecraft.mixin_related.extensions.vulkan_render_integration.INativeImageExt {

    @Shadow
    private long pixels;

    @Final
    @Shadow
    private long size;

    @Final
    @Shadow
    private int width;

    @Final
    @Shadow
    private NativeImage.Format format;

    @Final
    @Shadow
    private int height;

    @Shadow
    public abstract NativeImage mappedCopy(IntUnaryOperator operator);

    @Shadow
    public abstract NativeImage.Format format();

    @Inject(method = "_upload(IIIIIIIZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/NativeImage;checkAllocated()V", shift = At.Shift.AFTER), cancellable = true)
    public void redirectUploadInternal(int level, int offsetX, int offsetY, int unpackSkipPixels,
        int unpackSkipRows, int regionWidth, int regionHeight, boolean blur, CallbackInfo ci) {
        try {
            INativeImageExt self = (INativeImageExt) this;
            int targetId = self.tracecraft$getTargetID();

            AuxiliaryTextures.loadAndUpload((NativeImage) (Object) this, self, level, offsetX,
                offsetY, unpackSkipPixels, unpackSkipRows, regionWidth, regionHeight, blur);

            TextureProxy.queueUpload(pixels, (int) size, width, targetId, unpackSkipPixels,
                unpackSkipRows, offsetX, offsetY, regionWidth, regionHeight, level);
        } finally {
            if (blur) {
                this.close();
            }
        }
        ci.cancel();
    }

    @Inject(method = "close()V", at = @At(value = "HEAD"))
    public void closeImage(CallbackInfo ci) {
        INativeImageExt self = (INativeImageExt) this;
        NativeImage specularImage = self.tracecraft$getSpecularNativeImage();
        NativeImage normalImage = self.tracecraft$getNormalNativeImage();
        NativeImage flagImage = self.tracecraft$getFlagNativeImage();
        if (specularImage != null) {
            specularImage.close();
        }
        if (normalImage != null) {
            normalImage.close();
        }
        if (flagImage != null) {
            flagImage.close();
        }
    }

    @Override
    public NativeImage tracecraft$alignTo(NativeImage source) {
        int targetWidth = source.getWidth();
        int targetHeight = source.getHeight();
        NativeImage.Format targetFormat = source.format();

        if (width == targetWidth && height == targetHeight && format == targetFormat) {
            return (NativeImage) (Object) this;
        }

        NativeImage dest = new NativeImage(targetFormat, targetWidth, targetHeight, false);

        int srcChannels = this.format.components();
        int destChannels = targetFormat.components();
        int commonChannels = Math.min(srcChannels, destChannels);

        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int sampleX = (x < this.width) ? x : (x % this.width);
                int sampleY = (y < this.height) ? y : (y % this.height);

                long srcPixelPtr =
                    this.pixels + (sampleX + (long) sampleY * this.width) * srcChannels;
                long destPixelPtr =
                    ((com.tracecraft.mixin_related.extensions.vulkan_render_integration.INativeImageExt) (Object) dest).tracecraft$getPointer()
                        + (long) (x + (long) y * targetWidth) * destChannels;

                for (int c = 0; c < commonChannels; c++) {
                    byte val = MemoryUtil.memGetByte(srcPixelPtr + c);
                    MemoryUtil.memPutByte(destPixelPtr + c, val);
                }

                if (destChannels > srcChannels) {
                    for (int c = srcChannels; c < destChannels; c++) {
                        MemoryUtil.memPutByte(destPixelPtr + c, (byte) 0);
                    }
                }
            }
        }
        return dest;
    }

    @Override
    public long tracecraft$getPointer() {
        return pixels;
    }

    @Shadow
    protected abstract void checkAllocated();

    @Shadow
    public abstract int getWidth();

    @Shadow
    public abstract int getHeight();

    @Shadow
    protected abstract int getPixelABGR(int x, int y);

    @Shadow
    protected abstract void setPixelABGR(int x, int y, int color);

    @Shadow
    public abstract void close();

    @Inject(method = "downloadTexture(IZ)V", at = @At(value = "HEAD"), cancellable = true)
    public void redirectLoadFromTextureImage(int level, boolean removeAlpha, CallbackInfo ci) {
        RenderSystem.assertOnRenderThread();
        this.checkAllocated();
        RendererProxy.takeScreenshot(true, this.width, this.height, this.format.components(),
            this.pixels);
        if (removeAlpha && this.format.hasAlpha()) {
            for (int i = 0; i < this.getHeight(); i++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    this.setPixelABGR(j, i, this.getPixelABGR(j, i) | 255 << this.format.alphaOffset());
                }
            }
        }
        ci.cancel();
    }

    @Override
    public void tracecraft$loadFromTextureImageWithoutUI(int level, boolean removeAlpha) {
        RenderSystem.assertOnRenderThread();
        this.checkAllocated();
        RendererProxy.takeScreenshot(false, this.width, this.height, this.format.components(),
            this.pixels);
        if (removeAlpha && this.format.hasAlpha()) {
            for (int i = 0; i < this.getHeight(); i++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    this.setPixelABGR(j, i, this.getPixelABGR(j, i) | 255 << this.format.alphaOffset());
                }
            }
        }
    }
}

package com.tracecraft.mixins.vanilla_resource_tracker;

import com.tracecraft.client.texture.IdentifierInputStream;
import com.tracecraft.mixin_related.extensions.vanilla_resource_tracker.INativeImageExt;
import java.io.InputStream;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NativeImage.class)
public abstract class NativeImageMixins implements INativeImageExt {

    @Unique
    private int targetID = -1;

    @Unique
    private Identifier identifier = null;

    @Unique
    private NativeImage specularImage = null;

    @Unique
    private NativeImage normalImage = null;

    @Unique
    private NativeImage flagImage = null;

    @Inject(method = "read(Lnet/minecraft/client/texture/NativeImage$Format;Ljava/io/InputStream;)"
        +
        "Lnet/minecraft/client/texture/NativeImage;", at = @At(value = "RETURN"), cancellable = true)
    private static void readIdentifier(NativeImage.Format format, InputStream stream,
        CallbackInfoReturnable<NativeImage> cir) {
        NativeImage nativeImage = cir.getReturnValue();

        if (stream instanceof IdentifierInputStream) {
            Identifier identifier = ((IdentifierInputStream) stream).getResourceId();
            ((INativeImageExt) (Object) nativeImage).tracecraft$setIdentifier(identifier);
            cir.setReturnValue(nativeImage);
        } else {
            cir.setReturnValue(nativeImage);
        }
    }

    @Override
    public int tracecraft$getTargetID() {
        return targetID;
    }

    @Override
    public void tracecraft$setTargetID(int id) {
        this.targetID = id;
    }

    @Override
    public Identifier tracecraft$getIdentifier() {
        return identifier;
    }

    @Override
    public void tracecraft$setIdentifier(Identifier id) {
        this.identifier = id;
    }

    @Override
    public NativeImage tracecraft$getSpecularNativeImage() {
        return specularImage;
    }

    @Override
    public void tracecraft$setSpecularNativeImage(NativeImage image) {
        this.specularImage = image;
    }

    @Override
    public NativeImage tracecraft$getNormalNativeImage() {
        return normalImage;
    }

    @Override
    public void tracecraft$setNormalNativeImage(NativeImage image) {
        this.normalImage = image;
    }

    @Override
    public NativeImage tracecraft$getFlagNativeImage() {
        return flagImage;
    }

    @Override
    public void tracecraft$setFlagNativeImage(NativeImage image) {
        this.flagImage = image;
    }
}

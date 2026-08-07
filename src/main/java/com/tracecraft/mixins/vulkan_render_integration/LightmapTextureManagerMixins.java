package com.tracecraft.mixins.vulkan_render_integration;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tracecraft.client.UnsafeManager;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.ILightMapManagerExt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.profiler.Profilers;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixins implements ILightMapManagerExt {

    @Unique
    private float ambientLightFactor = 0;
    @Unique
    private float skyFactor = 0;
    @Unique
    private float blockFactor = 0;
    @Unique
    private boolean useBrightLightmap = false;
    @Unique
    private Vector3f skyLightColor = new Vector3f(0.0f, 0.0f, 0.0f);
    @Unique
    private float nightVisionFactor = 0;
    @Unique
    private float darknessScale = 0;
    @Unique
    private float darkenWorldFactor = 0;
    @Unique
    private float brightnessFactor = 0;
    @Unique
    private NativeImageBackedTexture tracecraft$texture;
    @Unique
    private NativeImage tracecraft$image;
    @Unique
    private Identifier tracecraft$textureIdentifier;

    @Mutable
    @Final
    @Shadow
    private SimpleFramebuffer lightmapFramebuffer;
    @Shadow
    private boolean dirty;
    @Shadow
    private float flickerIntensity;
    @Final
    @Shadow
    private GameRenderer renderer;
    @Final
    @Shadow
    private MinecraftClient client;

    // region <init>
    @Redirect(method = "<init>(Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/MinecraftClient;)V",
        at = @At(value = "NEW", target = "net/minecraft/client/gl/SimpleFramebuffer"))
    public SimpleFramebuffer cancelFramebufferConstruction(int width, int height,
        boolean useDepth) {
        return UnsafeManager.INSTANCE.allocateInstance(SimpleFramebuffer.class);
    }

    @Redirect(method = "<init>",
        at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/render/LightmapTextureManager;" +
                "lightmapFramebuffer:Lnet/minecraft/client/gl/SimpleFramebuffer;",
            opcode = Opcodes.PUTFIELD))
    public void writeNullFramebuffer(LightmapTextureManager instance, SimpleFramebuffer value) {
        this.lightmapFramebuffer = null;
    }

    @Redirect(method = "<init>(Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/MinecraftClient;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/SimpleFramebuffer;setTexFilter(I)V"))
    public void cancelFramebufferSetTexFilter(SimpleFramebuffer instance, int i) {

    }

    @Redirect(method = "<init>(Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/MinecraftClient;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/SimpleFramebuffer;setClearColor(FFFF)V"))
    public void cancelFramebufferSetClearColor(SimpleFramebuffer instance, float r, float g,
        float b, float a) {

    }

    @Redirect(method = "<init>(Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/MinecraftClient;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/SimpleFramebuffer;clear()V"))
    public void cancelFramebufferClear(SimpleFramebuffer instance) {

    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/MinecraftClient;)V",
        at = @At("TAIL"))
    public void initJavaLightmapTexture(GameRenderer renderer, MinecraftClient client,
        CallbackInfo ci) {
        this.tracecraft$texture = new NativeImageBackedTexture(16, 16, false);
        this.tracecraft$textureIdentifier = Identifier.of("tracecraft", "dynamic/light_map");
        this.client.getTextureManager()
            .registerTexture(this.tracecraft$textureIdentifier, this.tracecraft$texture);
        this.tracecraft$image = this.tracecraft$texture.getImage();
        if (this.tracecraft$image == null) {
            throw new IllegalStateException("Lightmap texture image was not initialized");
        }

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                this.tracecraft$image.setColorArgb(x, y, 0xFFFFFFFF);
            }
        }

        this.tracecraft$texture.setClamp(true);
        this.tracecraft$texture.setFilter(true, false);
        this.tracecraft$texture.upload();
    }
    // endregion

    // region <close>
    @Redirect(method = "close()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/SimpleFramebuffer;delete()V"))
    public void cancelFramebufferDelete(SimpleFramebuffer instance) {

    }

    @Inject(method = "close()V", at = @At("HEAD"))
    public void closeJavaLightmapTexture(CallbackInfo ci) {
        if (this.tracecraft$texture != null) {
            this.tracecraft$texture.close();
            this.tracecraft$texture = null;
            this.tracecraft$image = null;
            this.tracecraft$textureIdentifier = null;
        }
    }
    // endregion

    // region <disable>
    @Inject(method = "disable()V", at = @At(value = "HEAD"), cancellable = true)
    public void cancelDisable(CallbackInfo ci) {
        RenderSystem.setShaderTexture(2, 0);
        ci.cancel();
    }
    // endregion

    // region <enable>
    @Inject(method = "enable()V", at = @At(value = "HEAD"), cancellable = true)
    public void cancelEnable(CallbackInfo ci) {
        if (this.tracecraft$textureIdentifier != null) {
            RenderSystem.setShaderTexture(2, this.tracecraft$textureIdentifier);
        } else {
            RenderSystem.setShaderTexture(2, 0);
        }
        ci.cancel();
    }
    // endregion

    // region <update>
    @Shadow
    protected abstract float getDarknessFactor(float delta);

    @Shadow
    protected abstract float getDarkness(LivingEntity entity, float factor, float delta);

    @Inject(method = "update(F)V", at = @At(value = "HEAD"), cancellable = true)
    public void redirectUpdate(float delta, CallbackInfo ci) {
        if (this.dirty) {
            this.dirty = false;
            Profiler profiler = Profilers.get();
            profiler.push("lightTex");
            ClientWorld clientWorld = this.client.world;
            if (clientWorld != null && this.tracecraft$image != null && this.tracecraft$texture != null) {
                float f = clientWorld.getSkyBrightness(1.0F);
                float skyFactor;
                if (clientWorld.getLightningTicksLeft() > 0) {
                    skyFactor = 1.0F;
                } else {
                    skyFactor = f * 0.95F + 0.05F;
                }

                float
                    h =
                    this.client.options.getDarknessEffectScale()
                        .getValue()
                        .floatValue();
                float i = this.getDarknessFactor(delta) * h;
                float darknessScale = this.getDarkness(this.client.player, i, delta) * h;
                float k = this.client.player.getUnderwaterVisibility();
                float nightVisionFactor;
                if (this.client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                    nightVisionFactor = GameRenderer.getNightVisionStrength(this.client.player,
                        delta);
                } else if (k > 0.0F && this.client.player.hasStatusEffect(
                    StatusEffects.CONDUIT_POWER)) {
                    nightVisionFactor = k;
                } else {
                    nightVisionFactor = 0.0F;
                }

                Vector3f skyLightColor = new Vector3f(f, f, 1.0F).lerp(
                    new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
                float blockFactor = this.flickerIntensity + 1.5F;
                float
                    ambientLightFactor =
                    clientWorld.getDimension()
                        .ambientLight();
                boolean
                    useBrightLightmap =
                    clientWorld.getDimensionEffects()
                        .shouldBrightenLighting();
                float
                    o =
                    this.client.options.getGamma()
                        .getValue()
                        .floatValue();

                float darkenWorldFactor = this.renderer.getSkyDarkness(delta);
                float brightnessFactor = Math.max(0.0F, o - i);

                Vector3f workingColor = new Vector3f();
                for (int sky = 0; sky < 16; sky++) {
                    for (int block = 0; block < 16; block++) {
                        float skyBrightness = LightmapTextureManager.getBrightness(
                            clientWorld.getDimension(), sky) * skyFactor;
                        float blockBrightness = LightmapTextureManager.getBrightness(
                            clientWorld.getDimension(), block) * blockFactor;
                        float green = blockBrightness
                            * ((blockBrightness * 0.6F + 0.4F) * 0.6F + 0.4F);
                        float blue = blockBrightness * (blockBrightness * blockBrightness * 0.6F
                            + 0.4F);
                        workingColor.set(blockBrightness, green, blue);
                        if (useBrightLightmap) {
                            workingColor.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                            tracecraft$clamp(workingColor);
                        } else {
                            Vector3f skyContribution = new Vector3f(skyLightColor).mul(
                                skyBrightness);
                            workingColor.add(skyContribution);
                            workingColor.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                            if (darkenWorldFactor > 0.0F) {
                                Vector3f darkened = new Vector3f(workingColor).mul(0.7F, 0.6F,
                                    0.6F);
                                workingColor.lerp(darkened, darkenWorldFactor);
                            }
                        }

                        if (nightVisionFactor > 0.0F) {
                            float max = Math.max(workingColor.x(),
                                Math.max(workingColor.y(), workingColor.z()));
                            if (max < 1.0F) {
                                workingColor.lerp(new Vector3f(workingColor).mul(1.0F / max),
                                    nightVisionFactor);
                            }
                        }

                        if (!useBrightLightmap) {
                            if (darknessScale > 0.0F) {
                                workingColor.add(-darknessScale, -darknessScale, -darknessScale);
                            }
                            tracecraft$clamp(workingColor);
                        }

                        float gamma = this.client.options.getGamma()
                            .getValue()
                            .floatValue();
                        Vector3f eased = new Vector3f(tracecraft$easeOutQuart(workingColor.x()),
                            tracecraft$easeOutQuart(workingColor.y()),
                            tracecraft$easeOutQuart(workingColor.z()));
                        workingColor.lerp(eased, Math.max(0.0F, gamma - i));
                        workingColor.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                        tracecraft$clamp(workingColor);
                        workingColor.mul(255.0F);

                        int red = (int) workingColor.x();
                        int greenInt = (int) workingColor.y();
                        int blueInt = (int) workingColor.z();
                        this.tracecraft$image.setColorArgb(block, sky,
                            0xFF000000 | red << 16 | greenInt << 8 | blueInt);
                    }
                }
                this.tracecraft$texture.upload();

                this.ambientLightFactor = ambientLightFactor;
                this.skyFactor = skyFactor;
                this.blockFactor = blockFactor;
                this.useBrightLightmap = useBrightLightmap;
                this.skyLightColor = skyLightColor;
                this.nightVisionFactor = nightVisionFactor;
                this.darknessScale = darknessScale;
                this.darkenWorldFactor = darkenWorldFactor;
                this.brightnessFactor = brightnessFactor;
            }
            profiler.pop();
        }
        ci.cancel();
    }
    // endregion

    @Unique
    private static void tracecraft$clamp(Vector3f vec) {
        vec.set(MathHelper.clamp(vec.x(), 0.0F, 1.0F),
            MathHelper.clamp(vec.y(), 0.0F, 1.0F),
            MathHelper.clamp(vec.z(), 0.0F, 1.0F));
    }

    @Unique
    private float tracecraft$easeOutQuart(float x) {
        float f = 1.0F - x;
        return 1.0F - f * f * f * f;
    }

    @Override
    public int tracecraft$getTextureId() {
        return this.tracecraft$texture != null ? this.tracecraft$texture.getGlId() : 0;
    }

    public float tracecraft$getAmbientLightFactor() {
        return ambientLightFactor;
    }

    public float tracecraft$getSkyFactor() {
        return skyFactor;
    }

    public float tracecraft$getBlockFactor() {
        return blockFactor;
    }

    public boolean tracecraft$isUseBrightLightmap() {
        return useBrightLightmap;
    }

    public Vector3f tracecraft$getSkyLightColor() {
        return skyLightColor;
    }

    public float tracecraft$getNightVisionFactor() {
        return nightVisionFactor;
    }

    public float tracecraft$getDarknessScale() {
        return darknessScale;
    }

    public float tracecraft$getDarkenWorldFactor() {
        return darkenWorldFactor;
    }

    public float tracecraft$getBrightnessFactor() {
        return brightnessFactor;
    }
}

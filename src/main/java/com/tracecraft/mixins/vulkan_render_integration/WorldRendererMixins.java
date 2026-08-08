package com.tracecraft.mixins.vulkan_render_integration;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tracecraft.client.UnsafeManager;
import com.tracecraft.client.proxy.vulkan.BufferProxy;
import com.tracecraft.client.proxy.world.ChunkProxy;
import com.tracecraft.client.proxy.world.EntityProxy;
import com.tracecraft.client.proxy.world.PlayerProxy;
import com.tracecraft.client.vertex.StorageVertexConsumerProvider;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IGameRendererExt;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.ILightMapManagerExt;
import com.tracecraft.mixin_related.extensions.vulkan_render_integration.IOverlayTextureExt;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.WorldBorderRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererMixins {

    @Shadow
    private ClientLevel level;

    @Final
    @Shadow
    private Minecraft minecraft;

    @Final
    @Shadow
    private EntityRenderDispatcher entityRenderDispatcher;

    @Final
    @Shadow
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    @Shadow
    private ViewArea viewArea;

    @Shadow
    private Frustum cullingFrustum;

    @Final
    @Shadow
    private List<Entity> visibleEntities;

    @Shadow
    private int visibleEntityCount;

    @Shadow
    private double prevCamRotX;

    @Shadow
    private double prevCamRotY;

    @Final
    @Shadow
    private ObjectArrayList<SectionRenderDispatcher.RenderSection> visibleSections;

    @Shadow
    @Final
    private Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress;

    @Shadow
    @Final
    private Set<BlockEntity> globalBlockEntities;

    @Shadow
    @Final
    private WeatherEffectRenderer weatherEffectRenderer;

    @Shadow
    @Final
    private WorldBorderRenderer worldBorderRenderer;

    @Shadow
    private int ticks;
    @Shadow
    @Final
    private CloudRenderer cloudRenderer;
    // endregion

    // region <init>
    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/client/renderer/SkyRenderer"))
    private SkyRenderer cancelNewSkyRendering() {
        return UnsafeManager.INSTANCE.allocateInstance(SkyRenderer.class);
    }
    // endregion

    @Redirect(method = "needsUpdate()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SectionOcclusionGraph;invalidate()V"))
    public void cancelTerrainUpdateWithChunkRenderingDataPreparer(
        SectionOcclusionGraph instance) {

    }

    // region <close>
    @Redirect(method = "close()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;close()V"))
    public void cancelSkyRenderingClose(SkyRenderer instance) {

    }

    @Redirect(method = "onResourceManagerReload(Lnet/minecraft/server/packs/resources/ResourceManager;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;initOutline()V"))
    public void cancelReloadWithResourceManager(LevelRenderer instance) {

    }

    @Redirect(method = "allChanged()V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/renderer/SectionOcclusionGraph;waitAndReset(Lnet/minecraft/client/renderer/ViewArea;)V"))
    public void cancelReloadWithChunkRenderingDataPreparerSetStorage(
        SectionOcclusionGraph instance, ViewArea storage) {

    }

    @Redirect(method = "collectVisibleEntities(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;Ljava/util/List;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;isDetached()Z"))
    public boolean enablePlayerRendererInFirstPlayer(Camera instance) {
        return true;
    }

    @Redirect(method = "collectVisibleEntities(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;Ljava/util/List;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;shouldRender(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z"))
    public <E extends Entity> boolean loosenEntityFiltering(EntityRenderDispatcher instance,
        E entity, Frustum frustum, double x, double y, double z) {
        Vec3 vec3d = entity.position().subtract(new Vec3(x, y, z));
        double distance = vec3d.length();
        if (distance < 16 * 3) {
            return true;
        }
        return this.entityRenderDispatcher.shouldRender(entity, frustum, x, y, z);
    }

    // region <render>
    @Shadow
    protected abstract void setupRender(Camera camera, Frustum frustum, boolean hasForcedFrustum,
        boolean spectator);

    @Shadow
    protected abstract boolean collectVisibleEntities(Camera camera, Frustum frustum,
        List<Entity> output);

    @Shadow
    protected abstract boolean shouldShowEntityOutlines();

    @Shadow
    protected abstract void applyFrustum(Frustum frustum);

    @Shadow
    protected abstract boolean shouldRenderDarkDisc(float tickDelta);

    @Shadow
    protected abstract boolean doesMobEffectBlockSky(Camera camera);

    @Inject(method =
        "renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", at = @At("HEAD"), cancellable = true)
    public void redirectRender(GraphicsResourceAllocator allocator, DeltaTracker tickCounter,
        boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
        Matrix4f effectedRotationMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        PlayerProxy.setCameraPos(camera.getPosition());

        float f = tickCounter.getGameTimeDeltaPartialTick(false);
        RenderSystem.setShaderGameTime(this.level.getGameTime(), f);
        this.blockEntityRenderDispatcher.prepare(this.level, camera, this.minecraft.hitResult);
        this.entityRenderDispatcher.prepare(this.level, camera, this.minecraft.crosshairPickEntity);

        this.level.pollLightUpdates();
        this.level.getChunkSource().getLightEngine().runLightUpdates();

        Frustum frustum = this.cullingFrustum;

        Vec3 vec3d = camera.getPosition();
        double x = vec3d.x();
        double y = vec3d.y();
        double z = vec3d.z();

        this.setupRender(camera, frustum, false, false);

        boolean renderEntityOutline = this.collectVisibleEntities(camera, frustum,
            this.visibleEntities);

        Matrix4f viewMatrix = new Matrix4f(
            ((IGameRendererExt) gameRenderer).tracecraft$getRotationMatrix());
        Matrix4f effectedViewMatrix = new Matrix4f(effectedRotationMatrix);

        // fog
        float h = gameRenderer.getRenderDistance();
        boolean bl2 = this.minecraft.level.effects()
            .isFoggyAt(Mth.floor(x), Mth.floor(y))
            || this.minecraft.gui.getBossOverlay().shouldCreateWorldFog();
        Vector4f vector4f = FogRenderer.computeFogColor(camera, f, this.minecraft.level,
            this.minecraft.options.getEffectiveRenderDistance(), gameRenderer.getDarkenWorldAmount(f));
        FogParameters fog = FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_TERRAIN,
            vector4f, h, bl2, f);

        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        OverlayTexture overlayTexture = gameRenderer.overlayTexture();
        int overlayTextureID = ((IOverlayTextureExt) overlayTexture).tracecraft$getTexture()
            .getId();
        int endSkyTextureID = textureManager.getTexture(TheEndPortalRenderer.END_SKY_LOCATION)
            .getId();
        int endPortalTextureID = textureManager.getTexture(
            TheEndPortalRenderer.END_PORTAL_LOCATION).getId();
        ILightMapManagerExt lightMapManagerExt = (ILightMapManagerExt) (gameRenderer.lightTexture());
        BufferProxy.updateWorldUniform(camera, viewMatrix, effectedViewMatrix, projectionMatrix,
            overlayTextureID, fog, level, endSkyTextureID, endPortalTextureID,
            lightMapManagerExt.tracecraft$getTextureId());

        // Sky
        float tickDelta = tickCounter.getGameTimeDeltaPartialTick(false);
        float skyAngle = this.level.getTimeOfDay(tickDelta);
        int baseColor = this.level.getSkyColor(camera.getPosition(), tickDelta);

        DimensionSpecialEffects dimensionEffects = this.level.effects();
        int horizonColor = dimensionEffects.getSunriseOrSunsetColor(skyAngle);

        PoseStack matrixStack = new PoseStack();
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        matrixStack.mulPose(Axis.XP.rotationDegrees(skyAngle * 360.0F));
        Matrix4f rotationMatrix = matrixStack.last().pose();
        Vector3f sunDirection = rotationMatrix.transformPosition(0, 1, 0, new Vector3f())
            .normalize();
        matrixStack.popPose();

        boolean hasBlindnessOrDarkness = this.doesMobEffectBlockSky(camera);

        int submersionType = camera.getFluidInCamera().ordinal();

        int moonPhase = this.level.getMoonPhase();

        float rainGradient = this.level.getRainLevel(tickDelta);

        int sunTextureID = textureManager.getTexture(SkyRenderer.SUN_LOCATION).getId();

        int moonTextureID = textureManager.getTexture(SkyRenderer.MOON_LOCATION).getId();

        BufferProxy.updateSkyUniform(ARGB.redFloat(baseColor),
            ARGB.greenFloat(baseColor), ARGB.blueFloat(baseColor),
            ARGB.redFloat(horizonColor), ARGB.greenFloat(horizonColor),
            ARGB.blueFloat(horizonColor), ARGB.alphaFloat(horizonColor), sunDirection,
            dimensionEffects.skyType().ordinal(), dimensionEffects.isSunriseOrSunset(skyAngle),
            this.shouldRenderDarkDisc(tickDelta), hasBlindnessOrDarkness, submersionType, moonPhase,
            rainGradient, sunTextureID, moonTextureID);

        BufferProxy.updateMapping();

        // Entities
        EntityProxy.queueEntitiesBuild(camera, visibleEntities, this.entityRenderDispatcher,
            tickCounter, shouldShowEntityOutlines());

        Tuple<List<StorageVertexConsumerProvider>, EntityProxy.EntityRenderDataList> crumblingRenderData = EntityProxy.queueBlockEntitiesRebuild(
            viewArea, this.globalBlockEntities, destructionProgress,
            blockEntityRenderDispatcher, tickDelta);
        EntityProxy.queueCrumblingRebuild(camera, destructionProgress,
            this.minecraft.getBlockRenderer(), this.level, crumblingRenderData.getA(),
            crumblingRenderData.getB());

        EntityProxy.queueParticleRebuild(camera, tickDelta, frustum);

        if (renderBlockOutline) {
            EntityProxy.queueTargetBlockOutlineRebuild(camera, level);
        }

        EntityProxy.queueWeatherBuild(this.weatherEffectRenderer, this.worldBorderRenderer, this.level,
            camera, this.ticks, tickDelta);

        // clouds
        CloudStatus cloudRenderMode = this.minecraft.options.getCloudsType();
        if (cloudRenderMode != CloudStatus.OFF) {
            float k = this.level.effects().getCloudHeight();
            if (!Float.isNaN(k)) {
                float ticks = (float) this.ticks + f;
                int color = this.level.getCloudColor(f);
                float cloudHeight = k + 0.33F;
                this.cloudRenderer.render(color, cloudRenderMode, cloudHeight, null, null,
                    camera.getPosition(), ticks);
            }
        }

        // Chunks
        ChunkProxy.setStorage(viewArea);
        ChunkProxy.rebuild(camera);

        this.visibleEntities.clear();

        ci.cancel();
    }
    // endregion

    // region <setWorld>
    @Redirect(method = "setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/renderer/SectionOcclusionGraph;waitAndReset(Lnet/minecraft/client/renderer/ViewArea;)V"))
    public void cancelSetWorldChunkRenderingDataPreparerSetStorage(
        SectionOcclusionGraph instance, ViewArea storage) {

    }
    // endregion

    //region <setupTerrain>
    @Inject(method = "setupRender(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher;setCamera(Lnet/minecraft/world/phys/Vec3;)V", shift = At.Shift.AFTER), cancellable = true)
    public void cancelCullAndUpdateWithChunkRenderingDataPreparer(Camera camera, Frustum frustum,
        boolean hasForcedFrustum, boolean spectator, CallbackInfo ci, @Local ProfilerFiller profiler) {
//        PlayerProxy.setCameraPos(camera.getPos());
        profiler.pop();
        ci.cancel();
    }
    //endregion

    // region <addBuiltChunk>
    @Redirect(method = "addRecentlyCompiledSection(Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;)V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/renderer/SectionOcclusionGraph;schedulePropagationFrom(Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;)V"))
    public void cancelPropagateWithChunkRenderingDataPreparer(SectionOcclusionGraph instance,
        SectionRenderDispatcher.RenderSection builtChunk) {

    }
    // endregion

    // region <onChunkUnload>
    @Redirect(method = "onSectionBecomingNonEmpty(J)V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/renderer/SectionOcclusionGraph;schedulePropagationFrom(Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;)V"))
    public void cancelPropagateUnloadWithChunkRenderingDataPreparer(
        SectionOcclusionGraph instance, SectionRenderDispatcher.RenderSection builtChunk) {

    }
    // endregion

    // region <scheduleNeighborUpdates>
    @Redirect(method = "onChunkReadyToRender(Lnet/minecraft/world/level/ChunkPos;)V", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/renderer/SectionOcclusionGraph;onChunkReadyToRender(Lnet/minecraft/world/level/ChunkPos;)V"))
    public void cancelNeighborUpdatesWithChunkRenderingDataPreparer(
        SectionOcclusionGraph instance, ChunkPos chunkPos) {

    }
    // endregion

    // region <isRenderingReady>
    @Inject(method = "isSectionCompiled(Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void redirectIsRenderingReady(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        SectionRenderDispatcher.RenderSection builtChunk = viewArea.getRenderSectionAt(pos);

        if (builtChunk == null) {
            cir.setReturnValue(false);
        } else if (builtChunk.compiled.get().isEmpty(null)) {
            cir.setReturnValue(true);
        } else if (builtChunk.compiled.get() == ChunkProxy.PROCESSED) {
            cir.setReturnValue(ChunkProxy.isChunkReady(builtChunk));
        }
    }
    // endregion

    // region <>
    @Inject(method = "countRenderedSections()I", at = @At(value = "HEAD"), cancellable = true)
    public void fixGetCompletedChunkCount(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ChunkProxy.builtChunkNum - 54); // 54 + 10 = 64
    }
    // endregion
}

package com.tracecraft.mixins.vanilla_resource_tracker;

import com.tracecraft.mixin_related.extensions.vanilla_resource_tracker.ISpriteContentsExt;
import com.tracecraft.mixin_related.extensions.vanilla_resource_tracker.ISpriteExt;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureAtlasSprite.class)
public abstract class SpriteMixins implements ISpriteExt {

    @Final
    @Shadow
    private SpriteContents contents;

    public void tracecraft$setTargetID(int targetID) {
        ((ISpriteContentsExt) contents).tracecraft$setTargetID(targetID);
    }
}

package net.minecraft.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Camera {
    public Vec3 getPosition() { return new Vec3(0, 0, 0); }
    public BlockPos getBlockPosition() { return new BlockPos(0, 0, 0); }
    public float getXRot() { return 0.0f; }
    public float getYRot() { return 0.0f; }
    public boolean isDetached() { return false; }
    public Object getEntity() { return null; }
}

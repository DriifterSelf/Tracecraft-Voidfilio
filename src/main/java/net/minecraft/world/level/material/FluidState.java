package net.minecraft.world.level.material;

import net.minecraft.world.phys.Vec3;

public class FluidState {
    public boolean isEmpty() { return true; }
    public boolean is(Object type) { return false; }
    public float getHeight(Object world, Object pos) { return 0.0F; }
    public Fluid getType() { return new Fluid(); }
    public Vec3 getFlow(Object world, Object pos) { return Vec3.ZERO; }
    public boolean shouldRenderBackwardUpFace(Object world, Object pos) { return false; }
}

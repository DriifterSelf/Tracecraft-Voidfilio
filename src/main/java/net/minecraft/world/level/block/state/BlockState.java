package net.minecraft.world.level.block.state;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockState {
    public Block getBlock() { return new Block(); }
    public boolean isAir() { return false; }
    public int getLightEmission() { return 0; }
    public Object getShape(Object world, Object pos, Object context) { return null; }
    public VoxelShape getFaceOcclusionShape(Object direction) { return new VoxelShape(); }
    public FluidState getFluidState() { return new FluidState(); }
}

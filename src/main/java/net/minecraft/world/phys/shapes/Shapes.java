package net.minecraft.world.phys.shapes;

public class Shapes {
    public static boolean blockOccudes(Object shape1, Object shape2, Object direction) { return true; }
    public static VoxelShape empty() { return new VoxelShape(); }
    public static VoxelShape block() { return new VoxelShape(); }
    public static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) { return new VoxelShape(); }
}

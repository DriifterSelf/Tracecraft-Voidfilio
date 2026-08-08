package net.minecraft.client.multiplayer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.level.block.state.BlockState;

public class ClientLevel {
    public static class SkyType {
        public int ordinal() { return 0; }
    }
    public static class Effects {
        public SkyType skyType() { return new SkyType(); }
    }
    public Effects effects() { return new Effects(); }
    public BlockState getBlockState(BlockPos pos) { return new BlockState(); }
    public Object getWorldBorder() { return new WorldBorder(); }
    public TickRateManager tickRateManager() { return new TickRateManager(); }

    public static class WorldBorder {
        public boolean isWithinBounds(BlockPos pos) { return true; }
    }
}

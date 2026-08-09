package net.minecraft.world.level;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockAndTintGetter {
    default BlockState getBlockState(Object pos) {
        return new BlockState();
    }
    default float getShade(Object direction, boolean shade) {
        return 1.0F;
    }
}

package net.minecraft.client.renderer.chunk;

import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class SectionRenderDispatcher {
    public static class CompiledSection {
        public static final CompiledSection EMPTY = new CompiledSection();
        public boolean facesCanSeeEachother(Object d1, Object d2) { return true; }
        public java.util.List getRenderableBlockEntities() { return java.util.Collections.emptyList(); }
        public boolean isEmpty(Object renderType) { return true; }
    }

    public static class RenderSection {
        public int index;
        public AtomicReference<CompiledSection> compiled = new AtomicReference<>(CompiledSection.EMPTY);
        public CompiledSection getCompiled() {
            return compiled.get();
        }
        public boolean isDirty() { return false; }
        public boolean isDirtyFromPlayer() { return false; }
        public void setDirty(boolean dirty) {}
        public void setNotDirty() {}
        public boolean hasAllNeighbors() { return true; }
        public BlockPos getOrigin() { return new BlockPos(0, 0, 0); }
        public Object getSectionNode() { return null; }
        public void updateGlobalBlockEntities(Object entities) {}
    }

    public RenderSection[] sections = new RenderSection[0];
    public Vec3 getCameraPosition() { return new Vec3(0, 0, 0); }
}

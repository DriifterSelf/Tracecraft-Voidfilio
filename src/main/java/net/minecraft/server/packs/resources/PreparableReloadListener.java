package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface PreparableReloadListener {
    public interface PreparationBarrier {
        <T> CompletableFuture<T> wait(T value);
    }

    CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager resourceManager, Executor backgroundExecutor, Executor gameExecutor);
    
    default CompletableFuture<Void> reload(PreparationBarrier barrier, Object resourceManager, Executor backgroundExecutor, Executor gameExecutor) {
        if (resourceManager instanceof ResourceManager rm) {
            return reload(barrier, rm, backgroundExecutor, gameExecutor);
        }
        return CompletableFuture.completedFuture(null);
    }
}

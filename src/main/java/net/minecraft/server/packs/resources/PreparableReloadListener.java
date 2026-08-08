package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface PreparableReloadListener {
    interface SharedState {}
    interface PreparationBarrier {
        <T> CompletableFuture<T> wait(T value);
    }
    CompletableFuture<Void> reload(PreparationBarrier barrier, Object manager, Executor prepareExecutor, Executor applyExecutor);
}

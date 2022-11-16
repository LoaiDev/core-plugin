package me.loaidev.core.tasks;

import me.loaidev.core.CorePlugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class Task<I, O> {

    protected final @NotNull CorePlugin plugin;
    protected final @NotNull TaskScheduler scheduler;
    protected final @NotNull CompletableFuture<O> future;
    protected final boolean async;

    protected final @NotNull TaskFunction<I, O> function;

    public Task(@NotNull TaskFunction<I, O> function, @NotNull CorePlugin plugin, @NotNull TaskScheduler scheduler, boolean async) {
        this.function = function;
        this.plugin = plugin;
        this.scheduler = scheduler;
        this.future = new CompletableFuture<>();
        this.async = async;
    }

    public void scheduledExecute(I previous) {
        Runnable runnable = () -> this.execute(previous);
        if (async) {
            this.scheduler.runBukkitTaskAsynchronously(runnable);
        } else {
            this.scheduler.runBukkitSyncTask(runnable);
        }
    }

    public void execute(I previous) {
        try {
            this.function.execute(previous, this.getFuture());
        } catch (Throwable throwable) {
            this.getFuture().completeExceptionally(throwable);
        }
    }

    public @NotNull CompletableFuture<O> getFuture() {
        return this.future;
    }

    protected <T extends Task<O, ?>> T setCallbackTask(@NotNull T task, boolean scheduled) {
        this.getFuture().whenComplete((result, throwable) -> {
            if (throwable != null) {
                task.getFuture().completeExceptionally(throwable);
                return;
            }
            if (scheduled) {
                task.scheduledExecute(result);
                return;
            }
            task.execute(result);
        });
        return task;
    }
}

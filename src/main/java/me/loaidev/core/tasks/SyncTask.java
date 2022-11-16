package me.loaidev.core.tasks;

import me.loaidev.core.CorePlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SyncTask<I, O> extends Task<I, O> {

    public SyncTask(@NotNull TaskFunction<I, O> function, @NotNull CorePlugin plugin, @NotNull TaskScheduler scheduler) {
        super(function, plugin, scheduler, false);
    }

    public <T> @NotNull OutputtingSyncTask<O, T> then(Supplier<T> supplier) {
        return this.setCallbackTask(new OutputtingSyncTask<>(new TaskFunction<>(supplier), this.plugin, this.scheduler), false);
    }


    public @NotNull SyncTask<O, Void> then(@NotNull Runnable runnable) {
        return this.setCallbackTask(new SyncTask<>(new TaskFunction<>(runnable), this.plugin, this.scheduler), false);
    }


    public <T> AsyncTask.@NotNull OutputtingAsyncTask<O, T> thenAsync(Supplier<T> supplier) {
        return this.setCallbackTask(new AsyncTask.OutputtingAsyncTask<>(new TaskFunction<>(supplier), this.plugin, this.scheduler), true);
    }


    public @NotNull AsyncTask<O, Void> thenAsync(@NotNull Runnable runnable) {
        return this.setCallbackTask(new AsyncTask<>(new TaskFunction<>(runnable), this.plugin, this.scheduler), true);
    }

    public static class OutputtingSyncTask<I, O> extends SyncTask<I, O> {
        public OutputtingSyncTask(@NotNull TaskFunction<I, O> function, @NotNull CorePlugin plugin, @NotNull TaskScheduler scheduler) {
            super(function, plugin, scheduler);
        }

        public <T> @NotNull OutputtingSyncTask<O, T> then(@NotNull Function<O, T> function) {
            return this.setCallbackTask(new OutputtingSyncTask<>(new TaskFunction<>(function), this.plugin, this.scheduler), false);
        }

        public @NotNull SyncTask<O, Void> then(@NotNull Consumer<O> consumer) {
            return this.setCallbackTask(new SyncTask<>(new TaskFunction<>(consumer), this.plugin, this.scheduler), false);
        }

        public <T> AsyncTask.@NotNull OutputtingAsyncTask<O, T> thenAsync(@NotNull Function<O, T> function) {
            return this.setCallbackTask(new AsyncTask.OutputtingAsyncTask<>(new TaskFunction<>(function), this.plugin, this.scheduler), true);
        }

        public @NotNull AsyncTask<O, Void> thenAsync(@NotNull Consumer<O> consumer) {
            return this.setCallbackTask(new AsyncTask<>(new TaskFunction<>(consumer), this.plugin, this.scheduler), true);
        }
    }

}

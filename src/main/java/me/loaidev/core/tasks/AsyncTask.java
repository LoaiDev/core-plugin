package me.loaidev.core.tasks;

import me.loaidev.core.CorePlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AsyncTask<I, O> extends Task<I, O> {
    public AsyncTask(@NotNull TaskFunction<I, O> function, @NotNull CorePlugin plugin, @NotNull TaskScheduler scheduler) {
        super(function, plugin, scheduler, true);
    }

    public <T> @NotNull OutputtingAsyncTask<O, T> then(Supplier<T> supplier) {
        return this.setCallbackTask(new OutputtingAsyncTask<>(new TaskFunction<>(supplier), this.plugin, this.scheduler), false);
    }
    
    public @NotNull AsyncTask<O, Void> then(@NotNull Runnable runnable) {
        return this.setCallbackTask(new AsyncTask<>(new TaskFunction<>(runnable), this.plugin, this.scheduler), false);
    }

    public <T> SyncTask.@NotNull OutputtingSyncTask<O, T> thenSync(Supplier<T> supplier) {
        return this.setCallbackTask(new SyncTask.OutputtingSyncTask<>(new TaskFunction<>(supplier), this.plugin, this.scheduler), true);
    }

    public @NotNull SyncTask<O, Void> thenSync(@NotNull Runnable runnable) {
        return this.setCallbackTask(new SyncTask<>(new TaskFunction<>(runnable), this.plugin, this.scheduler), true);
    }

    public static class OutputtingAsyncTask<I, O> extends AsyncTask<I, O> {
        public OutputtingAsyncTask(@NotNull TaskFunction<I, O> function, @NotNull CorePlugin plugin, @NotNull TaskScheduler scheduler) {
            super(function, plugin, scheduler);
        }

        public <T> AsyncTask.@NotNull OutputtingAsyncTask<O, T> then(@NotNull Function<O, T> function) {
            return this.setCallbackTask(new AsyncTask.OutputtingAsyncTask<>(new TaskFunction<>(function), this.plugin, this.scheduler), false);
        }

        public @NotNull AsyncTask<O, Void> then(@NotNull Consumer<O> consumer) {
            return this.setCallbackTask(new AsyncTask<>(new TaskFunction<>(consumer), this.plugin, this.scheduler), false);
        }

        public <T> SyncTask.@NotNull OutputtingSyncTask<O, T> thenSync(@NotNull Function<O, T> function) {
            return this.setCallbackTask(new SyncTask.OutputtingSyncTask<>(new TaskFunction<>(function), this.plugin, this.scheduler), true);
        }

        public @NotNull SyncTask<O, Void> thenSync(@NotNull Consumer<O> consumer) {
            return this.setCallbackTask(new SyncTask<>(new TaskFunction<>(consumer), this.plugin, this.scheduler), true);
        }
    }
}

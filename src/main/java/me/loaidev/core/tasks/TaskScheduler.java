package me.loaidev.core.tasks;

import me.loaidev.core.CorePlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TaskScheduler {

    protected final @NotNull CorePlugin plugin;

    public TaskScheduler(@NotNull CorePlugin plugin) {
        this.plugin = plugin;
    }

    public <O> SyncTask.@NotNull OutputtingSyncTask<Void, O> sync(@NotNull Supplier<O> supplier) {
        return startTask(new SyncTask.OutputtingSyncTask<>(new TaskFunction<>(supplier), this.plugin, this));
    }

    public <O> AsyncTask.@NotNull OutputtingAsyncTask<Void, O> async(@NotNull Supplier<O> supplier) {
        return startTask(new AsyncTask.OutputtingAsyncTask<>(new TaskFunction<>(supplier), this.plugin, this));
    }

    public @NotNull SyncTask<Void, Void> sync(@NotNull Runnable runnable) {
        return startTask(new SyncTask<>(new TaskFunction<>(runnable), this.plugin, this));
    }

    public @NotNull AsyncTask<Void, Void> async(@NotNull Runnable runnable) {
        return startTask(new AsyncTask<>(new TaskFunction<>(runnable), this.plugin, this));
    }

    protected <C extends Task<Void, ?>> @NotNull C startTask(@NotNull C task) {
        task.scheduledExecute(null);
        return task;
    }

    public BukkitTask runBukkitSyncTask(Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    public BukkitTask runBukkitSyncTaskLater(Runnable runnable, long ticks) {
        return this.plugin.getServer().getScheduler().runTaskLater(plugin, runnable, ticks);
    }

    public BukkitTask runBukkitSyncTaskTimer(Runnable runnable, long delay, long timer) {
        return this.plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, timer);
    }

    public BukkitTask runBukkitTaskAsynchronously(Runnable runnable) {
        return this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public BukkitTask runBukkitTaskLaterAsynchronously(Runnable runnable, long ticks) {
        return this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, ticks);
    }

    public BukkitTask runBukkitTaskTimerAsynchronously(Runnable runnable, long delay, long timer) {
        return this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, timer);
    }
}

package me.loaidev.core;

import me.loaidev.core.tasks.TaskScheduler;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;


public abstract class CorePlugin extends JavaPlugin {

    @MonotonicNonNull
    protected TaskScheduler scheduler;

    public TaskScheduler getScheduler() {
        if (this.scheduler == null) {
            this.scheduler = new TaskScheduler(this);
        }
        return this.scheduler;
    }

    @Override
    public void onEnable() {

    }
}

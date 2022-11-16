package me.loaidev.core;

import me.loaidev.core.commands.dispatchers.CommandDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class User implements CommandDispatcher {

    private final Player player;

    public User(Player player) {
        this.player = player;
    }

    @Override
    public @NotNull UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public @NotNull CommandSender getBukkitSender() {
        return player;
    }
}

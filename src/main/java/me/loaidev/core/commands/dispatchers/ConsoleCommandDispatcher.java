package me.loaidev.core.commands.dispatchers;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ConsoleCommandDispatcher implements CommandDispatcher {
    private static final UUID CONSOLE_UUID = UUID.randomUUID();

    private final ConsoleCommandSender sender;

    public ConsoleCommandDispatcher(@NotNull ConsoleCommandSender sender) {
        this.sender = sender;
    }

    @Override
    public @NotNull UUID getUUID() {
        return CONSOLE_UUID;
    }

    @Override
    public @NotNull CommandSender getBukkitSender() {
        return sender;
    }
}

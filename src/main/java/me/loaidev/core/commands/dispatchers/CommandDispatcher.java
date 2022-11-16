package me.loaidev.core.commands.dispatchers;

import me.loaidev.core.User;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface CommandDispatcher extends ForwardingAudience.Single {

    @NotNull UUID getUUID();

    default boolean isPlayer() {
        return this instanceof User;
    }

    @NotNull CommandSender getBukkitSender();

    default @NotNull Audience audience() {
        return getBukkitSender();
    }
}

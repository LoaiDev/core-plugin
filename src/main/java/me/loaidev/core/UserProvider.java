package me.loaidev.core;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface UserProvider<U extends User> {
    U getUser(Player player);

    U getUser(UUID uuid);
}

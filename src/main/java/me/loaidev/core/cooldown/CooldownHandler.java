package me.loaidev.core.cooldown;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class CooldownHandler<K> {

    @NotNull
    protected final ConcurrentMap<K, Long> cooldowns = new ConcurrentHashMap<>();

    public boolean isOnCooldown(@NotNull K key) {
        return this.cooldowns.containsKey(key) && this.cooldowns.get(key) > System.currentTimeMillis();
    }

    public long getCooldownExpirationTime(@NotNull K key) {
        return this.cooldowns.getOrDefault(key, 0L);
    }

    public long getTimeSecondsLeft(@NotNull K key) {
        if (this.isOnCooldown(key)) {
            return TimeUnit.MILLISECONDS.toSeconds(this.getCooldownExpirationTime(key) - System.currentTimeMillis());
        }
        return 0L;
    }
}

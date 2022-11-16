package me.loaidev.core.storage;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class YamlStorage extends YamlConfiguration {

    protected final @NotNull File file;

    public YamlStorage(@NotNull File file) {
        this.file = file;
        this.reload();
    }

    public YamlStorage put(@NotNull String key, @NotNull Object value) {
        this.set(key, value);
        return this;
    }

    public <T> List<T> getList(@NotNull String path, @NotNull Class<T> clazz) {
        return this.getList(path, clazz, new ArrayList<>());
    }

    public  <T> List<T> getList(String path, Class<T> clazz, List<T> def) {
        List<?> list = getList(path);
        if (list == null || list.isEmpty()) {
            return def;
        }
        List<T> result = new ArrayList<>();
        for (Object o : list) {
            if (clazz.isInstance(o)) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }

    public void addToList(@NotNull String path, @NotNull Object value) {
        List<Object> list = getList(path, Object.class);
        list.add(value);
        this.set(path, list);
    }


    public void save() {
        try {
            this.save(file);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error saving file: " + file.getPath(), ex);
        }
    }

    public void reload() {
        try {
            this.load(file);
        } catch (FileNotFoundException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "File not found: " + file.getPath());
        } catch (IOException | InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error loading file: " + file.getPath(), ex);
        }
    }
}

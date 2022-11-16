package me.loaidev.core.storage;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class YamlStorageProvider {

    @NotNull
    protected final File folder;

    public YamlStorageProvider(@NotNull File folder) {
        this.folder = folder;
        if (folder.exists()) {
            if (!folder.isDirectory()) {
                throw new IllegalArgumentException("File " + folder.getPath() + " is not a directory");
            }
        } else {
            if (!folder.mkdirs()) {
                throw new IllegalArgumentException("Failed to create directory " + folder.getPath());
            }
        }
    }

    public YamlStorage getStorage(@NotNull String name) {
        File file = new File(folder, name + ".yml");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    throw new IllegalArgumentException("Failed to create file " + file.getPath());
                }
            } catch (IOException ex) {
                throw new IllegalArgumentException("Failed to create file " + file.getPath());
            }
        }
        return new YamlStorage(file);
    }

    public void deleteStorage(@NotNull String name) {
        File file = new File(folder, name + ".yml");
        if (file.exists()) {
            if (!file.delete()) {
                throw new IllegalArgumentException("Failed to delete file " + file.getPath());
            }
        }
    }
}

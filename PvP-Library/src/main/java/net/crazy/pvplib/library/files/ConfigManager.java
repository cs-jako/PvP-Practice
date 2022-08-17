package net.crazy.pvplib.library.files;

import net.crazy.pvplib.library.MCLogger;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {
    private HashMap<String, Object> updateList = new HashMap<>();
    private final File file;
    private final YamlConfiguration config;

    public ConfigManager(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean fileExists() {
        return file.exists();
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public Double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public ConfigManager update(String path, Object value) {
        updateList.put(path, value);
        return this;
    }

    public void save() {
        updateList.forEach(config::set);

        try {
            this.config.save(file);
        } catch (IOException exception) {
            MCLogger.log(MCLogger.LogType.ERROR, exception.getMessage());
        }
    }
}

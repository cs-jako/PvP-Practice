package net.crazy.pvplib;

import org.bukkit.plugin.java.JavaPlugin;

public class Library extends JavaPlugin {
    public static Library instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}

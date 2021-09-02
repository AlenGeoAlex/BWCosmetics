package me.alen_alex.bwcosmetics;

import me.alen_alex.bwcosmetics.utility.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class BWCosmetics extends JavaPlugin {

    private static BWCosmetics plugin;
    private static FileUtils fileUtils;

    @Override
    public void onEnable() {
        plugin = this;
        fileUtils = new FileUtils();
    }

    @Override
    public void onDisable() {

    }

    public static BWCosmetics getPlugin() {
        return plugin;
    }

    public static FileUtils getFileUtils() {
        return fileUtils;
    }
}

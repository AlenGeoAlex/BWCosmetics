package me.alen_alex.bwcosmetics;

import me.alen_alex.bwcosmetics.config.Configuration;
import me.alen_alex.bwcosmetics.data.DataStorage;
import me.alen_alex.bwcosmetics.utility.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class BWCosmetics extends JavaPlugin {

    private static BWCosmetics plugin;
    private static FileUtils fileUtils;
    private static Configuration configuration;
    private static DataStorage storage;
    @Override
    public void onEnable() {
        plugin = this;
        fileUtils = new FileUtils();
        configuration = new Configuration();
        if(!configuration.createConfiguration(this)){
            getLogger().severe("Unable to create configuration file.");
            getLogger().severe("Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        storage = new DataStorage(this,getConfiguration().isUsingMysql());
        storage.build();
        storage.tryConnection();
        storage.createDatabase();
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

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static DataStorage getStorage() {
        return storage;
    }
}

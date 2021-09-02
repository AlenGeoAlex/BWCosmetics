package me.alen_alex.bwcosmetics;

import com.andrei1058.bedwars.api.BedWars;
import me.alen_alex.bwcosmetics.config.Configuration;
import me.alen_alex.bwcosmetics.cosmetics.CosmeticManager;
import me.alen_alex.bwcosmetics.data.Storage;
import me.alen_alex.bwcosmetics.utility.FileUtils;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BWCosmetics extends JavaPlugin {

    private static BWCosmetics plugin;
    private static FileUtils fileUtils;
    private static Configuration configuration;
    private static Storage storage;
    private static CosmeticManager cosmeticManager;
    private static BedWars bwAPI;
    @Override
    public void onEnable() {
        plugin = this;
        if(!getServer().getPluginManager().isPluginEnabled("BedWars1058")){
            getLogger().severe("Unable to locate Bedwars1058.");
            getLogger().severe("Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else{
            bwAPI = Bukkit.getServicesManager().getRegistration(BedWars .class).getProvider();
        }
        fileUtils = new FileUtils();
        configuration = new Configuration();
        if(!configuration.createConfiguration(this)){
            getLogger().severe("Unable to create configuration file.");
            getLogger().severe("Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        WorkloadScheduler.intializeThread();
        storage = new Storage(this,getConfiguration().isUsingMysql());
        storage.build();
        storage.tryConnection();
        storage.createDatabase();
        cosmeticManager.loadBowTrail();
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

    public static Storage getStorage() {
        return storage;
    }

    public static CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }

    public static BedWars getBwAPI() {
        return bwAPI;
    }
}

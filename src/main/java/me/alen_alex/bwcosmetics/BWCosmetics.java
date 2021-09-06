package me.alen_alex.bwcosmetics;

import com.andrei1058.bedwars.api.BedWars;

import me.alen_alex.bwcosmetics.cache.CacheManager;
import me.alen_alex.bwcosmetics.config.Configuration;
import me.alen_alex.bwcosmetics.cosmetics.CosmeticManager;
import me.alen_alex.bwcosmetics.data.Storage;
import me.alen_alex.bwcosmetics.listener.PlayerJoinEvent;
import me.alen_alex.bwcosmetics.listener.PlayerLeaveEvent;
import me.alen_alex.bwcosmetics.listener.PlayerProjectileLaunchEvent;
import me.alen_alex.bwcosmetics.listener.bw1058.GameEndEvent;
import me.alen_alex.bwcosmetics.listener.bw1058.GameStateChangeEvent;
import me.alen_alex.bwcosmetics.listener.citizen.CitizensEnableEvent;
import me.alen_alex.bwcosmetics.playerdata.PlayerCosmeticsManager;
import me.alen_alex.bwcosmetics.task.Cooldowns;
import me.alen_alex.bwcosmetics.utility.FileUtils;
import me.alen_alex.bwcosmetics.utility.RandomUtility;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BWCosmetics extends JavaPlugin {

    private static BWCosmetics plugin;
    private static FileUtils fileUtils;
    private static Configuration configuration;
    private static Storage storage;
    private static CosmeticManager cosmeticManager;
    private static BedWars bwAPI;
    private static PlayerCosmeticsManager playerManager;
    private static Cooldowns cooldownTasks;
    private static WorkloadScheduler scheduler;
    private static RandomUtility randomUtility;
    private static CacheManager cacheManager;
    private boolean bedwarsEnabled = false,citizensEnabled = false;
    private NPCRegistry npcRegistry;
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
            bedwarsEnabled = true;
        }
        if(!getServer().getPluginManager().isPluginEnabled("Citizens")){
            getLogger().severe("Citizens is missing shopkeeper won't be used!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else{
            npcRegistry = CitizensAPI.getNPCRegistry();
            citizensEnabled = true;
            getLogger().info("Hooked with Citizens for shopkeeper");
        }
        fileUtils = new FileUtils();
        configuration = new Configuration();
        if(!configuration.createConfiguration(this)){
            getLogger().severe("Unable to create configuration file.");
            getLogger().severe("Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        cacheManager = new CacheManager(this);
        if(!cacheManager.buildCache()){
            getLogger().severe("Unable to create cache file.");
            getLogger().severe("Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        scheduler = new WorkloadScheduler();
        scheduler.intializeThread();
        storage = new Storage(this,getConfiguration().isUsingMysql());
        storage.build();
        storage.tryConnection();
        storage.createDatabase();
        randomUtility = new RandomUtility(this);
        cosmeticManager = new CosmeticManager();
        cosmeticManager.loadCosmetics();
        playerManager = new PlayerCosmeticsManager();
        registerListeners();
        cooldownTasks = new Cooldowns();
        cooldownTasks.runTaskTimerAsynchronously(this,100L,10L);
        getLogger().info("------------------------------------------------");
        getLogger().info("Enabled Plugin");
        getLogger().info("------------------------------------------------");

    }

    @Override
    public void onDisable() {
        storage.disconnect();
    }

    public void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerProjectileLaunchEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
        if(bedwarsEnabled){
            getLogger().info("Starting to register Bw1058 listeners...");
            getServer().getPluginManager().registerEvents(new GameStateChangeEvent(),this);
            getServer().getPluginManager().registerEvents(new GameEndEvent(), this);
        }
        if(citizensEnabled)
            getServer().getPluginManager().registerEvents(new CitizensEnableEvent(), this);
    }

    public static BWCosmetics getPlugin() {
        return plugin;
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Storage getStorage() {
        return storage;
    }

    public CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }

    public BedWars getBwAPI() {
        return bwAPI;
    }

    public PlayerCosmeticsManager getPlayerManager() {
        return playerManager;
    }

    public Cooldowns getCooldownTasks() {
        return cooldownTasks;
    }

    public WorkloadScheduler getScheduler() {
        return scheduler;
    }

    public RandomUtility getRandomUtility() {
        return randomUtility;
    }

    public boolean isBedwarsEnabled() {
        return bedwarsEnabled;
    }

    public boolean isCitizensEnabled() {
        return citizensEnabled;
    }

    public NPCRegistry getNpcRegistry() {
        return npcRegistry;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}

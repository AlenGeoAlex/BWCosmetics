package me.alen_alex.bwcosmetics.cache;

import de.leonhard.storage.Json;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.FileUtils;

public class CacheManager {

    private BWCosmetics plugin;
    private Json cacheFile;


    public CacheManager(BWCosmetics plugin) {
        this.plugin = plugin;
    }

    public boolean buildCache(){
        this.cacheFile = BWCosmetics.getPlugin().getFileUtils().createJSONFile(this.plugin,"watchdog.json","cache");
        return cacheFile != null;
    }

}

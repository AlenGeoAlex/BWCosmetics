package me.alen_alex.bwcosmetics.cache;

import de.leonhard.storage.Json;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class CacheManager {

    private BWCosmetics plugin;
    private Json cacheFile;


    public CacheManager(BWCosmetics plugin) {
        this.plugin = plugin;
    }

    public boolean buildCache(){
        this.cacheFile = BWCosmetics.getPlugin().getFileUtils().createJSONFile(this.plugin,plugin.getResource("watchdog.json"),"watchdog.json","cache");
        return cacheFile != null;
    }

    public void addToCache(int id){
        List<String> cacheList = cacheFile.getStringList("cachedNPC");
        cacheList.add(String.valueOf(id));
        cacheFile.set("cachedNPC",cacheList);
    }

    public boolean contains(int id){
        List<String> cacheList = cacheFile.getStringList("cachedNPC");
        return cacheList.contains(String.valueOf(id));
    }

    public List<Integer> fetchList(){
        List<String> cacheList = cacheFile.getStringList("cachedNPC");
        List<Integer> returnList = new ArrayList<>();
        cacheList.forEach((id) -> {
            returnList.add(Integer.parseInt(id));
        });
        return returnList;
    }

    public boolean removeFromCache(int id){
        if(contains(id)){
            List<String> cacheList = cacheFile.getStringList("cachedNPC");
            cacheList.remove(String.valueOf(id));
            return true;
        }else
            return false;
    }

    public void clearCacheFile(){
        cacheFile.set("cachedNPC", new ArrayList<String>());
    }

    public int size(){
        return cacheFile.getStringList("cachedNPC").size();
    }







}

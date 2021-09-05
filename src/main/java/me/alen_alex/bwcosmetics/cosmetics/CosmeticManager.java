package me.alen_alex.bwcosmetics.cosmetics;

import com.andrei1058.bedwars.api.arena.IArena;
import de.leonhard.storage.Json;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.Shopkeeper;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.ShopkeeperSkins;
import me.alen_alex.bwcosmetics.cosmetics.bowtrail.BowTrial;
import me.alen_alex.bwcosmetics.utility.SkinType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class CosmeticManager {

    private HashMap<String, BowTrial> cachedBowTrial = new HashMap<String,BowTrial>();
    private HashMap<String, ShopkeeperSkins> cachedSkins = new HashMap<String, ShopkeeperSkins>();
    private HashMap<IArena, Shopkeeper> currentGames = new HashMap<IArena,Shopkeeper>();

    public void loadCosmetics(){
        if(BWCosmetics.getConfiguration().isBowtrialEnabled())
            loadBowTrail();
        if(BWCosmetics.getConfiguration().isShopkeeperEnabled() && BWCosmetics.getPlugin().isCitizensEnabled())
            loadShopkeeperSkins();
    }

    private void loadBowTrail(){
        YamlConfiguration config =YamlConfiguration.loadConfiguration(BWCosmetics.getConfiguration().getBowTrailConfig().getFile());
        if(config != null){
            for(String keySet : config.getKeys(false)){
                try {
                    if(cachedBowTrial.containsKey(keySet))
                        BWCosmetics.getPlugin().getLogger().warning("Bowtrail - "+keySet+" already exist! will be overridden!");
                    cachedBowTrial.put(keySet,new BowTrial(config.getString(keySet+".name"), config.getStringList(keySet+".particle"),config.getInt(keySet+".cooldowninSeconds"),config.getStringList(keySet+".description")));
                }catch (IllegalArgumentException e){
                    BWCosmetics.getPlugin().getLogger().warning("Disabling Bowtrail "+config.getString(keySet+".name")+" as it seems its impossible to fetch/load data");
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void loadShopkeeperSkins(){
        YamlConfiguration config2 = YamlConfiguration.loadConfiguration(BWCosmetics.getConfiguration().getShopKeeperConfig().getFile());
        if(config2 != null){
            for(String keySet : config2.getKeys(false)){
                try {
                    if(cachedSkins.containsKey(keySet))
                        BWCosmetics.getPlugin().getLogger().warning("Skin - "+keySet+" already exist! will be overridden!");
                    if(config2.getString(keySet+".skintype").toUpperCase().equalsIgnoreCase("ENTITY")){
                        cachedSkins.put(keySet,new ShopkeeperSkins(keySet, SkinType.ENTITY,config2.getString(keySet+".entity"), config2.getString(keySet+".menuItem")));
                    }else if (config2.getString(keySet+".skintype").toUpperCase().equalsIgnoreCase("PLAYERSKIN")){
                        cachedSkins.put(keySet,new ShopkeeperSkins(keySet, SkinType.PLAYERSKIN,config2.getString(keySet+".skinTexture"),config2.getString(keySet+".skinSignature"), config2.getString(keySet+".menuItem")));
                    }else
                        BWCosmetics.getPlugin().getLogger().warning("Unknown skin type found for "+keySet+". Skin won't be registered nor can be equipped!");
                }catch (IllegalArgumentException e){
                    System.out.println(keySet+".name");
                    BWCosmetics.getPlugin().getLogger().warning("Disabling ShopkeeperSkin for "+keySet+" as it seems its impossible to fetch/load data");
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public HashMap<String, BowTrial> getCachedBowTrial() {
        return cachedBowTrial;
    }

    public HashMap<String, ShopkeeperSkins> getCachedSkins() {
        return cachedSkins;
    }

    public boolean containsBowTrail(String name){
        return cachedBowTrial.containsKey(name);
    }

    public boolean containsShopkeeperskin(String name){
        return cachedSkins.containsKey(name);
    }

    public HashMap<IArena, Shopkeeper> getCurrentGames() {
        return currentGames;
    }

    public boolean containsCurrentGame(IArena arena){
        return currentGames.containsKey(arena);
    }

    public Shopkeeper getCurrentGameSkins(IArena arena){
        if(containsCurrentGame(arena))
            return currentGames.get(arena);
        else
            return null;
    }

}

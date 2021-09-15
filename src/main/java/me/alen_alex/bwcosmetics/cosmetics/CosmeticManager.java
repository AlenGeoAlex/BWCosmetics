package me.alen_alex.bwcosmetics.cosmetics;

import com.andrei1058.bedwars.api.arena.IArena;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.Shopkeeper;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.ShopkeeperSkins;
import me.alen_alex.bwcosmetics.cosmetics.bowtrail.BowTrial;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.SkinType;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.DragonRide;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.HorseRide;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.Toystick;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.types.WitherRide;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CosmeticManager {

    private HashMap<String, BowTrial> cachedBowTrial = new HashMap<String,BowTrial>();
    private HashMap<String, ShopkeeperSkins> cachedSkins = new HashMap<String, ShopkeeperSkins>();
    private HashMap<IArena, Shopkeeper> currentGames = new HashMap<IArena,Shopkeeper>();

    public void loadCosmetics(){
        if(BWCosmetics.getPlugin().getConfiguration().isBowtrialEnabled())
            loadBowTrail();
        if(BWCosmetics.getPlugin().getConfiguration().isShopkeeperEnabled() && BWCosmetics.getPlugin().isCitizensEnabled())
            loadShopkeeperSkins();
    }

    private void loadBowTrail(){
        YamlConfiguration config =YamlConfiguration.loadConfiguration(BWCosmetics.getPlugin().getConfiguration().getBowTrailConfig().getFile());
        if(config != null){
            for(String keySet : config.getKeys(false)){
                try {
                    if(cachedBowTrial.containsKey(keySet))
                        BWCosmetics.getPlugin().getLogger().warning("Bowtrail - "+keySet+" already exist! will be overridden!");
                    cachedBowTrial.put(keySet,new BowTrial(config.getString(keySet+".name"), config.getStringList(keySet+".particle"),config.getInt(keySet+".cooldowninSeconds"),config.getStringList(keySet+".description"),config.getStringList(keySet+".disabled-worlds")));
                }catch (IllegalArgumentException e){
                    BWCosmetics.getPlugin().getLogger().warning("Disabling Bowtrail "+config.getString(keySet+".name")+" as it seems its impossible to fetch/load data");
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private void loadShopkeeperSkins(){
        YamlConfiguration config2 = YamlConfiguration.loadConfiguration(BWCosmetics.getPlugin().getConfiguration().getShopKeeperConfig().getFile());
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

    public void startPlayerVictorDance(Player player){
        VictoryDanceType type = BWCosmetics.getPlugin().getPlayerManager().getPlayer(player).getVictoryDanceType();
        switch (type){
            case DRAGONRIDE:
                new DragonRide(BWCosmetics.getPlugin(),player,player.getLocation()).startRide();
                break;
            case WITHERRIDE:
                new WitherRide(BWCosmetics.getPlugin(),player,player.getLocation()).startRide();
                break;
            case TOYSTICK:
                new Toystick(BWCosmetics.getPlugin(),player,player.getLocation()).startdance();
                break;
            case HORSERIDE:
                new HorseRide(BWCosmetics.getPlugin(),player,player.getLocation()).startRide();
                break;
            default:
                BWCosmetics.getPlugin().getLogger().warning("Unknown victory dance type "+type.name());
        }
    }

}

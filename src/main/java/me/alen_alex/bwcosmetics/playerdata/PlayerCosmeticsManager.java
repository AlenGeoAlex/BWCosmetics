package me.alen_alex.bwcosmetics.playerdata;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCosmeticsManager {

    private HashMap<Player, PlayerCosmetic> cachedPlayer = new HashMap<Player, PlayerCosmetic>();

    public boolean contains(Player player){
        return cachedPlayer.containsKey(player);
    }

    public boolean contains(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if( player == null)
            return false;

        return cachedPlayer.containsKey(player);
    }

    public PlayerCosmetic getPlayer(Player player){
        if(contains(player)){
            return cachedPlayer.get(player);
        }else{
            return null;
        }
    }

    public PlayerCosmetic getPlayer(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if( player == null)
            return null;

        return getPlayer(player);
    }

    public HashMap<Player, PlayerCosmetic> getCachedPlayer() {
        return cachedPlayer;
    }
}

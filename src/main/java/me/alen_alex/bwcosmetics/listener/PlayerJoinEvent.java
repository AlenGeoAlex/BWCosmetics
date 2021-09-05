package me.alen_alex.bwcosmetics.listener;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.playerdata.PlayerCosmetic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(!BWCosmetics.getPlugin().getStorage().doUserExist(uuid)){
            BWCosmetics.getPlugin().getStorage().registerUser(uuid);
        }else{
            BWCosmetics.getPlugin().getPlayerManager().getCachedPlayer().put(event.getPlayer(), new PlayerCosmetic(uuid));
        }
    }

    @EventHandler
    public void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event){
        UUID uuid = event.getUniqueId();
        if(!BWCosmetics.getPlugin().getStorage().doUserExist(uuid))
            BWCosmetics.getPlugin().getStorage().registerUser(uuid);
    }

}

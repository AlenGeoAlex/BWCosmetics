package me.alen_alex.bwcosmetics.listener;

import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(BWCosmetics.getPlugin().getPlayerManager().contains(uuid)){
            BWCosmetics.getPlugin().getPlayerManager().getPlayer(uuid).save();
            BWCosmetics.getPlugin().getPlayerManager().getPlayer(uuid).destroy();
            BWCosmetics.getPlugin().getPlayerManager().getCachedPlayer().remove(uuid);
        }
    }



}

package me.alen_alex.bwcosmetics.listener.bw1058;

import com.andrei1058.bedwars.api.arena.IArena;
import me.alen_alex.bwcosmetics.BWCosmetics;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class GameEndEvent implements Listener {

    @EventHandler
    public void onGameEndEvent(com.andrei1058.bedwars.api.events.gameplay.GameEndEvent event){
        if(BWCosmetics.getPlugin().isCitizensEnabled() && BWCosmetics.getPlugin().getConfiguration().isShopkeeperEnabled()) {
            IArena arena = event.getArena();
            if (!BWCosmetics.getPlugin().getCosmeticManager().containsCurrentGame(arena)) {
                BWCosmetics.getPlugin().getCosmeticManager().getCurrentGameSkins(arena).removeSpawnedNPC();
                BWCosmetics.getPlugin().getCosmeticManager().getCurrentGames().remove(arena);
            }
        }

        if(BWCosmetics.getPlugin().getConfiguration().isVictoryDanceEnabled()){
            event.getWinners().forEach((uuid) -> {
                if(BWCosmetics.getPlugin().getCooldownTasks().containVictoryDance(uuid)) {
                    return;
                }
                if(Bukkit.getPlayer(uuid) != null){
                    Player player = Bukkit.getPlayer(uuid);
                    if(player.isOnline() && BWCosmetics.getPlugin().getPlayerManager().contains(uuid)){
                        if(BWCosmetics.getPlugin().getPlayerManager().getPlayer(player).hasVictoryDance()){
                            BWCosmetics.getPlugin().getCosmeticManager().startPlayerVictorDance(player);
                        }
                    }
                }
            });
        }
    }

}

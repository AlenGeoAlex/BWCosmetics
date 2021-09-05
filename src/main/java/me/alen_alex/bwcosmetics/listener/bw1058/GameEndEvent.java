package me.alen_alex.bwcosmetics.listener.bw1058;

import com.andrei1058.bedwars.api.arena.IArena;
import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameEndEvent implements Listener {

    @EventHandler
    public void onGameEndEvent(com.andrei1058.bedwars.api.events.gameplay.GameEndEvent event){
        if(BWCosmetics.getPlugin().isCitizensEnabled()) {
            IArena arena = event.getArena();
            if (!BWCosmetics.getPlugin().getCosmeticManager().containsCurrentGame(arena)) {
                BWCosmetics.getPlugin().getCosmeticManager().getCurrentGameSkins(arena).removeSpawnedNPC();
                BWCosmetics.getPlugin().getCosmeticManager().getCurrentGames().remove(arena);
            }
        }
    }

}

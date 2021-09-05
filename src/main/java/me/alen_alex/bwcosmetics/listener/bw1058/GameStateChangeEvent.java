package me.alen_alex.bwcosmetics.listener.bw1058;

import com.andrei1058.bedwars.api.arena.GameState;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.Shopkeeper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStateChangeEvent implements Listener {

    @EventHandler
    public void onGameStateChangeEvent(com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent event) {
        if (BWCosmetics.getPlugin().isCitizensEnabled()) {
            if (event.getNewState() != GameState.playing)
                return;

            BWCosmetics.getCosmeticManager().getCurrentGames().put(event.getArena(), new Shopkeeper(BWCosmetics.getPlugin(), event.getArena()));
            if (BWCosmetics.getCosmeticManager().containsCurrentGame(event.getArena())) {
                Shopkeeper currentGameSkin = BWCosmetics.getCosmeticManager().getCurrentGameSkins(event.getArena());
                currentGameSkin.selectRandomPlayer();
                Bukkit.getScheduler().runTaskLater(BWCosmetics.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        currentGameSkin.spawnShopkeepers();
                    }
                }, 30L);
            }

        }
    }

}

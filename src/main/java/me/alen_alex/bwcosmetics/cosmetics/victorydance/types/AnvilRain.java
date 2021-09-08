package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class AnvilRain extends VictoryDance implements Listener {

    public AnvilRain(Plugin plugin, Player player, Location location, VictoryDanceType victoryDanceType) {
        super(plugin, player, location, VictoryDanceType.ANVILRAIN);
    }

    @Override
    public void startdance() {
        if(!hasUsePermission()){

        }
    }
}

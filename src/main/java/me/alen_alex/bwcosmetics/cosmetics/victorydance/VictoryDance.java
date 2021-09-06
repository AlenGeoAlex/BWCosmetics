package me.alen_alex.bwcosmetics.cosmetics.victorydance;

import me.alen_alex.bwcosmetics.utility.VictoryDanceType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VictoryDance {

    private Plugin plugin;
    private Player player;
    private Location location;
    private VictoryDanceType victoryDanceType;
    private World playerCurrentWorld;

    public VictoryDance(Plugin plugin,Player player, Location location) {
        this.plugin = plugin;
        this.player = player;
        this.location = location;
        this.playerCurrentWorld = player.getWorld();
    }

    public VictoryDance(Plugin plugin,Player player, Location location, VictoryDanceType victoryDanceType) {
        this.plugin = plugin;
        this.player = player;
        this.location = location;
        this.victoryDanceType = victoryDanceType;
        this.playerCurrentWorld = player.getWorld();
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public World getPlayerCurrentWorld() {
        return playerCurrentWorld;
    }

    public VictoryDanceType getVictoryDanceType() {
        return victoryDanceType;
    }

    public void setVictoryDanceType(VictoryDanceType victoryDanceType) {
        this.victoryDanceType = victoryDanceType;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public boolean hasPermission(){
        return this.player.hasPermission("bwc.use.victorydance");
    }

    public boolean hasUsePermission(){
        return hasPermission() && this.player.hasPermission("bwc.victorydance."+victoryDanceType.name());
    }

}

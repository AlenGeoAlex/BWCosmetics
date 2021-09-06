package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Toystick extends VictoryDance implements Listener {
    public Toystick(Plugin plugin, Player player){
        super(plugin,player,player.getLocation(), VictoryDanceType.TOYSTICK);
    }
    @Override
    public void startdance(){
        if(!hasUsePermission()){
            return;
        }

    }
}

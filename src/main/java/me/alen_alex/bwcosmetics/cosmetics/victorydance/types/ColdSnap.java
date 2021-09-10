package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


public class ColdSnap extends VictoryDance implements Listener {


    public ColdSnap(Plugin plugin, Player player, Location location) {
        super(plugin, player, location, VictoryDanceType.COLDSNAP);
    }

    private void startRide(){
        if(!hasUsePermission())
            return;

        if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getColdSnapDisabledWorlds().contains(getPlayerCurrentWorld().getName())){
            MessageUtils.sendMessage(getPlayer(),BWCosmetics.getPlugin().getMessages().getDisabledWorldVictoryDance(),false);
            return;
        }





    }


}

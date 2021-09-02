package me.alen_alex.bwcosmetics.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class PlayerProjectileLaunchEvent implements Listener {

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event){
        if(event.isCancelled())
            return;
        if(!(event.getEntity().getShooter() instanceof Player))
            return;
        Player player = ((Player) event.getEntity().getShooter()).getPlayer();

        //if()


    }

}

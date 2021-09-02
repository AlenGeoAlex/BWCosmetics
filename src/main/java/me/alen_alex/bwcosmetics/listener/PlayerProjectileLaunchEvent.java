package me.alen_alex.bwcosmetics.listener;

import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerProjectileLaunchEvent implements Listener {

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event){
        if(event.isCancelled())
            return;
        if(!(event.getEntity().getShooter() instanceof Player))
            return;
        Player player = ((Player) event.getEntity().getShooter()).getPlayer();

        if(BWCosmetics.getBwAPI().getArenaUtil().isPlaying(player)){
            if(event.getEntity() instanceof Arrow){
                final Arrow arrow = (Arrow) event.getEntity();
                (new BukkitRunnable(){
                    @Override
                    public void run() {

                    }
                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(),0L,10L);
            }
        }


    }

}

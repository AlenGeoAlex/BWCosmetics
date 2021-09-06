package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class WitherRide extends VictoryDance implements Listener {

    private Wither wither;

    public WitherRide(Plugin plugin, Player player, Location location) {
        super(plugin, player, location, VictoryDanceType.WITHERRIDE);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public Wither getWither() {
        return wither;
    }


    private void destroy(){
        HandlerList.unregisterAll(this);
        wither.remove();
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void startRide(){
        //CHECK FOR WORLDS
        if(!hasUsePermission()){
            return;
        }
        this.wither = (Wither) getPlayerCurrentWorld().spawnEntity(getLocation(), EntityType.WITHER);
        this.wither.setMaxHealth(20.0D);
        this.wither.setPassenger((Entity) getPlayer());
        (new BukkitRunnable() {
            @Override
            public void run() {
                if(getPlayer() == null){
                    cancel();
                    destroy();
                }
                if((!(getPlayer().getWorld() == getPlayerCurrentWorld())) || wither.getPassenger() != getPlayer() || (getPlayer().getVehicle() != wither) || getPlayer().isOnline()){
                    cancel();
                    destroy();
                }

                wither.setVelocity(getPlayer().getEyeLocation().clone().getDirection().normalize().multiply(0.5D));
            }
        }).runTaskTimer(getPlugin(),1L,20L);
    }

    @EventHandler
    private void onPlayerInteractEvent(PlayerInteractEvent event){
        if(event.getPlayer().getVehicle() != this.wither)
            return;

        WitherSkull skull = (WitherSkull)  event.getPlayer().getWorld().spawn(event.getPlayer().getEyeLocation().clone().add(event.getPlayer().getEyeLocation().getDirection().normalize().multiply(2)), WitherSkull.class);
        skull.setShooter((ProjectileSource) getPlayer());
        skull.setVelocity(event.getPlayer().getEyeLocation().clone().getDirection().normalize().multiply(3));
    }

    @EventHandler
    private void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event){
        if(event.getEntity() instanceof Wither)
            event.setCancelled(true);
    }

}

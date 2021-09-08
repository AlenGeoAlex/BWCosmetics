package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.GameMode;
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
        if(this.getPlayer().isOnline())
            getPlayer().setGameMode(GameMode.SURVIVAL);
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void startRide(){
        if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getWitherDisabledWorlds().contains(getPlayer().getWorld().getName())) {
            MessageUtils.sendMessage(getPlayer(),BWCosmetics.getPlugin().getMessages().getDisabledWorldVictoryDance(),false);
            return;
        }
        if(!hasUsePermission()){
            return;
        }
        this.wither = (Wither) getPlayerCurrentWorld().spawnEntity(getLocation(), EntityType.WITHER);
        this.wither.setCustomName(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getWitherName(getPlayer().getName()));
        this.wither.setCustomNameVisible(true);
        this.wither.setMaxHealth(20.0D);
        this.wither.setPassenger((Entity) getPlayer());
        getPlayer().setGameMode(GameMode.CREATIVE);
        (new BukkitRunnable() {
            @Override
            public void run() {
                if(getPlayer() == null){
                    cancel();
                    destroy();
                }
                if((getPlayer().getWorld() != getPlayerCurrentWorld()) || wither.getPassenger() != getPlayer() || (getPlayer().getVehicle() != wither) || !getPlayer().isOnline()){
                    cancel();
                    destroy();
                }
                wither.setVelocity(getPlayer().getEyeLocation().clone().getDirection().multiply(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getWitherVelocityMultiplier()));
            }
        }).runTaskTimer(getPlugin(),1L,BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getWitherRefreshRate());
    }

    @EventHandler
    private void onPlayerInteractEvent(PlayerInteractEvent event){
        if(event.getPlayer().getVehicle() instanceof Wither) {
            if (event.getPlayer().getVehicle() != this.wither)
                return;
            WitherSkull skull = (WitherSkull) event.getPlayer().getWorld().spawn(event.getPlayer().getEyeLocation().clone().add(event.getPlayer().getEyeLocation().getDirection().normalize().multiply(2)), WitherSkull.class);
            skull.setShooter((ProjectileSource) getPlayer());
            skull.setVelocity(event.getPlayer().getEyeLocation().clone().getDirection().normalize().multiply(3));
        }
    }

    @EventHandler
    private void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event){
        if (event.getEntity() instanceof Wither) {
            if(event.getEntity() == this.wither) {
                event.setCancelled(true);
            }
        }
    }

}

package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.BlockUtils;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class DragonRide extends VictoryDance implements Listener {

    private EnderDragon enderDragon;
    private ArmorStand armorStand;

    public DragonRide(Plugin plugin, Player player, Location location) {
        super(plugin, player, location, VictoryDanceType.DRAGONRIDE);
        getPlugin().getServer().getPluginManager().registerEvents(this,getPlugin());
    }

    public EnderDragon getEnderDragon() {
        return enderDragon;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    private void destroy(){
        HandlerList.unregisterAll(this);
        if(enderDragon != null) {
            if (!enderDragon.isDead() && enderDragon.isValid())
                enderDragon.remove();
        }
        if(armorStand != null) {
            if(armorStand.isValid())
                armorStand.remove();
        }
        if(getPlayer().isOnline()){
            getPlayer().setGameMode(GameMode.SURVIVAL);
        }
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void startRide(){
        if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getDragonRideDisabled().contains(getPlayerCurrentWorld().getName())){
            MessageUtils.sendMessage(getPlayer(),BWCosmetics.getPlugin().getMessages().getDisabledWorldVictoryDance(),false);
            return;
        }

        if(!hasUsePermission())
            return;

        enderDragon = (EnderDragon) getPlayerCurrentWorld().spawnEntity(getLocation(), EntityType.ENDER_DRAGON);
        armorStand = (ArmorStand) getPlayerCurrentWorld().spawnEntity(getLocation(),EntityType.ARMOR_STAND);
        this.enderDragon.setCustomName(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getDragonName(getPlayer().getName()));
        this.enderDragon.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        enderDragon.setPassenger((Entity) getPlayer());
        enderDragon.setMetadata("CUSTOM_DRAGON", new FixedMetadataValue(getPlugin(),""));
        armorStand.setMetadata("FAKE_TARGET",new FixedMetadataValue(getPlugin(), ""));
        getPlayer().setGameMode(GameMode.CREATIVE);
        (new BukkitRunnable(){
            @Override
            public void run() {

                if(getPlayer() == null){
                    cancel();
                    destroy();
                }
                if((!(getPlayer().getWorld() == getPlayerCurrentWorld())) || enderDragon.getPassenger() != getPlayer() || (getPlayer().getVehicle() != enderDragon) || !getPlayer().isOnline()){
                    cancel();
                    destroy();
                }

                if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().isBreakEnviormentEnabled()){
                    BlockUtils.getBlocksInRadius(enderDragon.getLocation(),15).forEach((block) -> {
                        block.setType(Material.AIR);
                    });
                }

                armorStand.teleport(getPlayer().getEyeLocation().add(getPlayer().getEyeLocation().clone().getDirection().normalize().multiply(30)));

            }
        }).runTaskTimer(getPlugin(),1L,BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getDragonRefreshRate());
    }

    @EventHandler
    private void onEntityTargetEvent(EntityTargetEvent event){
        if(event.getEntity() instanceof EnderDragon) {
            if (event.getEntity() == enderDragon) {
                if(event.getEntity().getPassenger() instanceof Player) {
                    if (this.enderDragon.hasMetadata("CUSTOM_DRAGON") && enderDragon.getPassenger() == getPlayer()) {
                        if(event.getTarget() != getArmorStand()){
                            event.setTarget(armorStand);
                        }
                    }
                }

            }
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        if(event.getPlayer() == getPlayer()){
            if(getPlayer().getVehicle() instanceof EnderDragon){
                if(getPlayer().getVehicle() == this.enderDragon){
                    Fireball fireBall = getPlayerCurrentWorld().spawn(getPlayer().getEyeLocation().clone().add(getPlayer().getLocation().getDirection().normalize().multiply(2)),Fireball.class);
                    fireBall.setShooter((ProjectileSource) getPlayer());
                    fireBall.setVelocity(getPlayer().getEyeLocation().clone().getDirection().normalize().multiply(3));
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if(event.getEntity() instanceof EnderDragon){
            if(event.getEntity() == this.enderDragon) {
                if (event.getEntity().hasMetadata("CUSTOM_DRAGON"))
                    event.setCancelled(true);
            }

            if(event.getEntity() instanceof Player){
                if(event.getEntity() == getPlayer() && event.getEntity().getPassenger() == getPlayer() && event.getEntity().getVehicle().hasMetadata("CUSTOM_DRAGON"))
                    event.setCancelled(true);
            }
        }
    }



}

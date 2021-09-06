package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.utility.VictoryDanceType;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
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
        enderDragon.remove();
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void startRide(){
        //TODO CHECK PERMISSIONS
        enderDragon = (EnderDragon) getPlayerCurrentWorld().spawnEntity(getLocation(), EntityType.ENDER_DRAGON);
        armorStand = (ArmorStand) getPlayerCurrentWorld().spawnEntity(getLocation(),EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        enderDragon.setPassenger((Entity) getPlayer());
        enderDragon.setMetadata("CUSTOM_DRAGON", new FixedMetadataValue(getPlugin(),""));
        armorStand.setMetadata("FAKE_TARGET",new FixedMetadataValue(getPlugin(), ""));
        (new BukkitRunnable(){
            @Override
            public void run() {
                //TODO CHECK FOR WORLDS

            }
        }).runTaskTimer(getPlugin(),1L,20L);
    }

    @EventHandler
    private void onEntityTargetEvent(EntityTargetEvent event){
        if(event.getEntity() == enderDragon){

        }
    }



}

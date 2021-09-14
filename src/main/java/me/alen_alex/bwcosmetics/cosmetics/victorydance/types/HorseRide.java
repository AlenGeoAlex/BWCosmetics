package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.ItemUtils;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HorseRide extends VictoryDance implements Listener {

    private Horse horse;

    public HorseRide(Plugin plugin, Player player, Location location) {
        super(plugin, player, location, VictoryDanceType.HORSERIDE);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public Horse getHorse() {
        return horse;
    }

    public void startRide(){
        if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getDragonRideDisabled().contains(getPlayerCurrentWorld().getName())){
            MessageUtils.sendMessage(getPlayer(),BWCosmetics.getPlugin().getMessages().getDisabledWorldVictoryDance(),false);
            return;
        }

        if(!hasUsePermission())
            return;
        addPlayerToCooldown(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getHorseRideCooldown());
        this.horse = (Horse) getPlayerCurrentWorld().spawnEntity(getLocation(), EntityType.HORSE);
        this.horse.setCustomName(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().getHorseName(getPlayer().getName()));
        this.horse.setTamed(true);
        this.horse.setOwner((AnimalTamer) getPlayer());
        this.horse.getInventory().setSaddle(ItemUtils.getItem(Material.SADDLE));
        this.horse.setPassenger((Entity) getPlayer());
        if(BWCosmetics.getPlugin().getConfiguration().getVictoryDanceConfig().isClearHorseOnWorldUnload()) {
            (new BukkitRunnable() {
                @Override
                public void run() {
                    if (getPlayer() == null) {
                        cancel();
                        destroy();
                    }

                    if ((getPlayer().getWorld() != getPlayerCurrentWorld()) || horse.getPassenger() != getPlayer() || (getPlayer().getVehicle() != horse) || !getPlayer().isOnline()) {
                        cancel();
                        destroy();
                    }

                }
            }).runTaskTimer(getPlugin(), 1L, 40);
        }
    }

    private void destroy(){
        HandlerList.unregisterAll(this);
        if(horse != null)
            horse.remove();

        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event){
        if(event.isCancelled() || event.getPlayer().getVehicle() !=  this.horse)
            return;

        if (event.getTo().getBlockX() == event.getFrom().getBlockX() && event.getTo().getBlockY() == event.getFrom().getBlockY() && event.getTo().getBlockZ() == event.getFrom().getBlockZ()) {
            return;
        }

        if(event.getPlayer().getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)
            event.getPlayer().getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.COBBLESTONE);

    }



}

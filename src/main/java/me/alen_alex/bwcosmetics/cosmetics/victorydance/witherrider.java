package me.alen_alex.bwcosmetics.cosmetics.victorydance;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.config.VictorydanceConfig;
import me.alen_alex.bwcosmetics.cosmetics.Victorydances;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class witherrider {
    public void witherrider(Player player){
        if (VictorydanceConfig.getvictorydanceConfig().getBoolean("witherrider.enabled") == false) {
            return;
        }
        Wither wither = (Wither) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.WITHER);
        wither.setPassenger(player);
        wither.setCustomName(MessageUtils.parseColor(VictorydanceConfig.getvictorydanceConfig()
                .getString("witherrider.withername").replace("%player%", player.getName())));
        player.sendMessage(MessageUtils.parseColor(VictorydanceConfig.getvictorydanceConfig().getString("witherrider.message")
        ));
        World world = player.getWorld();

        new BukkitRunnable() {
            @Override
            public void run() {
                Vector direction = player.getEyeLocation().clone().getDirection().normalize().multiply(0.5);
                wither.setVelocity(direction);
                //Victorydances.victorydancecountdown.put(player,true);
                if(!(player.getWorld() == world)){
                    wither.remove();
                   Victorydances.canusedance.put(player,true);
                    MessageUtils.parseColor("&aYour Wither Rider has been removed since u changed worlds");
                    this.cancel();
                }
                else if(player.getVehicle() != wither){
                    wither.remove();
                    Victorydances.canusedance.put(player,true);
                    this.cancel();
                }
                else if(wither.getPassenger() == null){
                    wither.remove();
                    Victorydances.canusedance.put(player,true);
                    this.cancel();
                }
                else if(Victorydances.canusedance.get(player).booleanValue()){
                    wither.remove();
                    this.cancel();
                }
                else{

                }
            }
        }.runTaskTimer(BWCosmetics.getPlugin(),0L,10L);
    }

}

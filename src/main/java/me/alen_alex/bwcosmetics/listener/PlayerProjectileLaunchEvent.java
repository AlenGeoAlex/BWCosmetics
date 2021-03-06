package me.alen_alex.bwcosmetics.listener;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.bowtrail.BowTrial;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerProjectileLaunchEvent implements Listener {

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent event){
        /*
        Returns if the event is cancelled or the trigger is not a player
         */
        if(event.isCancelled())
            return;
        if(!(event.getEntity().getShooter() instanceof Player))
            return;
        final Player player = ((Player) event.getEntity().getShooter()).getPlayer();
        /*
        Check whether the given player is currently on game and he has selected a bow trail
         */
        if(BWCosmetics.getPlugin().getBwAPI().getArenaUtil().isPlaying(player) && BWCosmetics.getPlugin().getPlayerManager().getPlayer(player).hasBowTrail()){
            if(BWCosmetics.getPlugin().getCooldownTasks().bowTrialContains(player.getUniqueId())){
                if(BWCosmetics.getPlugin().getConfiguration().isCooldownMessagesEnabled())
                    MessageUtils.sendMessage(player, BWCosmetics.getPlugin().getMessages().getInCooldown(BWCosmetics.getPlugin().getCooldownTasks().getBowTrialCooldown().get(player.getUniqueId())),false);
                return;
            }
            /*
            To check whether the player has permission to use any of the bow trails & player not in a cooldown
             */
            if(player.hasPermission("bwc.use.bowtrail")) {
                final BowTrial bowTrial = BWCosmetics.getPlugin().getPlayerManager().getPlayer(player).getPlayerBowTrail();
                /*
                Check if the current world of player is enabled or not
                 */
                if (bowTrial.isWorldEnabled(player)) {
                /*
                To check whether the player has permission to use the particular selected bow trail!
                 */
                    if (bowTrial.hasPermission(player)) {
                        if (event.getEntity() instanceof Arrow) {
                            if (BWCosmetics.getPlugin().getConfiguration().isBowTrailArrowEnabled()) {
                                Arrow eventEntity = (Arrow) event.getEntity();

                                (new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                /*
                                Checks if the entity arrow is not empty...If empty it will cancel the Runnable
                                 */
                                        if (eventEntity == null)
                                            cancel();
                                /*
                                Cancels the task timer incase if the arrow reached the ground or arrow has been lost
                                 */
                                        if (eventEntity.isOnGround() || !(eventEntity.isValid()) || eventEntity.isDead())
                                            cancel();

                                        bowTrial.display(eventEntity.getLocation());
                                    }
                                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(), 0L, 2L);
                                BWCosmetics.getPlugin().getCooldownTasks().addToBowCooldown(player.getUniqueId(), bowTrial.getCooldown());
                                return;
                            }
                        }


                        if (event.getEntity() instanceof Egg) {
                            if (BWCosmetics.getPlugin().getConfiguration().isBowTrailEggEnabled()) {
                                Egg eventEntity = (Egg) event.getEntity();

                                (new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                /*
                                Checks if the entity arrow is not empty...If empty it will cancel the Runnable
                                 */
                                        if (eventEntity == null)
                                            cancel();
                                /*
                                Cancels the task timer incase if the arrow reached the ground or arrow has been lost
                                 */
                                        if (eventEntity.isOnGround() || !(eventEntity.isValid()) || eventEntity.isDead())
                                            cancel();

                                        bowTrial.display(eventEntity.getLocation());
                                    }
                                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(), 0L, 2L);
                                BWCosmetics.getPlugin().getCooldownTasks().addToBowCooldown(player.getUniqueId(), bowTrial.getCooldown());
                                return;
                            }
                        }

                        if (event.getEntity() instanceof FishHook) {
                            if (BWCosmetics.getPlugin().getConfiguration().isBowTrailFishhookEnabled()) {
                                FishHook eventEntity = (FishHook) event.getEntity();

                                (new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                /*
                                Checks if the entity arrow is not empty...If empty it will cancel the Runnable
                                 */
                                        if (eventEntity == null)
                                            cancel();
                                /*
                                Cancels the task timer incase if the arrow reached the ground or arrow has been lost
                                 */
                                        if (eventEntity.isOnGround() || !(eventEntity.isValid()) || eventEntity.isDead())
                                            cancel();

                                        bowTrial.display(eventEntity.getLocation());
                                    }
                                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(), 0L, 2L);
                                BWCosmetics.getPlugin().getCooldownTasks().addToBowCooldown(player.getUniqueId(), bowTrial.getCooldown());
                                return;
                            }
                        }

                        if (event.getEntity() instanceof Snowball) {
                            if (BWCosmetics.getPlugin().getConfiguration().isBowTrailSnowballEnabled()) {
                                Snowball eventEntity = (Snowball) event.getEntity();

                                (new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                /*
                                Checks if the entity arrow is not empty...If empty it will cancel the Runnable
                                 */
                                        if (eventEntity == null)
                                            cancel();
                                /*
                                Cancels the task timer incase if the arrow reached the ground or arrow has been lost
                                 */
                                        if (eventEntity.isOnGround() || !(eventEntity.isValid()) || eventEntity.isDead())
                                            cancel();


                                        bowTrial.display(eventEntity.getLocation());
                                    }
                                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(), 0L, 2L);
                                BWCosmetics.getPlugin().getCooldownTasks().addToBowCooldown(player.getUniqueId(), bowTrial.getCooldown());
                                return;
                            }
                        }

                        if (event.getEntity() instanceof Fireball) {
                            if (BWCosmetics.getPlugin().getConfiguration().isBowTrailFireballEnabled()) {
                                Fireball eventEntity = (Fireball) event.getEntity();

                                (new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                /*
                                Checks if the entity arrow is not empty...If empty it will cancel the Runnable
                                 */
                                        if (eventEntity == null)
                                            cancel();
                                /*
                                Cancels the task timer incase if the arrow reached the ground or arrow has been lost
                                 */
                                        if (eventEntity.isOnGround() || !(eventEntity.isValid()) || eventEntity.isDead())
                                            cancel();


                                        bowTrial.display(eventEntity.getLocation());
                                    }
                                }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(), 0L, 2L);
                                BWCosmetics.getPlugin().getCooldownTasks().addToBowCooldown(player.getUniqueId(), bowTrial.getCooldown());
                                return;
                            }
                        }

                    }
                }
            }
        }


    }

}

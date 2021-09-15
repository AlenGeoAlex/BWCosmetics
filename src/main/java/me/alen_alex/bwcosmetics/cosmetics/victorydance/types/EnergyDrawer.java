package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;

import com.andrei1058.bedwars.api.arena.IArena;
import me.Abhigya.core.particle.particlelib.ParticleBuilder;
import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.LineUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EnergyDrawer extends VictoryDance {

    private ParticleBuilder particleBuilder;

    public EnergyDrawer(Plugin plugin, Player player, Location location) {
        super(plugin, player, location, VictoryDanceType.ENERGYDRAWER);
        this.particleBuilder = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY);
    }

    public void startRide(){
        IArena arena = BWCosmetics.getPlugin().getBwAPI().getArenaUtil().getArenaByPlayer(getPlayer());
        if(arena == null)
            return;
        List<Location> teamSpawns = new ArrayList<>();
        List<List<Location>> lineLocations = new ArrayList<>();
        arena.getTeams().forEach((team) -> {
            if(team.getBed() != null)
                teamSpawns.add(team.getBed());
        });
        getPlayer().setFlying(true);
        getPlayer().setAllowFlight(true);
        (new BukkitRunnable(){
            @Override
            public void run() {
                //TODO ADD LOCATION CHECKS ONLINE CHECKS AND ALL
                if(getPlayer().getWorld() != getPlayerCurrentWorld()){
                    cancel();
                }

                teamSpawns.forEach((teamspawn) -> {
                    try {
                        lineLocations.add(LineUtils.getLineLocations(teamspawn,getLocation()).get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).runTaskTimerAsynchronously(BWCosmetics.getPlugin(),1L,10L);

        if(lineLocations.isEmpty())
            return;

        getPlugin().getServer().getScheduler().runTaskTimer(getPlugin(), new Runnable() {
            @Override
            public void run() {
                Workload workload = ()  -> {
                    lineLocations.forEach((locationList) -> {
                        if(!locationList.isEmpty()){
                            locationList.forEach((location) -> {
                                particleBuilder.setLocation(location).display();
                            });
                        }
                    });
                };
                BWCosmetics.getPlugin().getScheduler().getSyncThread().add(workload);
            }
        },1L,40L);




    }

}

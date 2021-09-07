package me.alen_alex.bwcosmetics.cosmetics.bowtrail;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.Abhigya.core.particle.particlelib.ParticleBuilder;
import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BowTrial {

    private final String name;
    private final int cooldown;
    private final List<ParticleBuilder> particleBuilders = new ArrayList<ParticleBuilder>();
    private final ArrayList<String> description;
    private List<String> disabledWorlds  = new ArrayList<String>();

    public BowTrial(@NotNull String name,@NotNull List<String> particles,@NotNull int cooldown, List<String> description,List<String> disabledWorlds) {
        this.name = name;
        particles.forEach((particleEffect -> {
            this.particleBuilders.add(new ParticleBuilder(ParticleEffect.valueOf(particleEffect)));
        }));
        this.cooldown = cooldown;
        this.description = new ArrayList<>(description);
        this.disabledWorlds = disabledWorlds;
    }

    public void display(Location location){
        Workload workload = () -> {
            particleBuilders.forEach((particleBuilder -> {
                particleBuilder.setLocation(location).display();
            }));
        };
        BWCosmetics.getPlugin().getScheduler().getSyncThread().add(workload);
    }

    public boolean hasPermission(Player player){
        return player.hasPermission("bwc.bowtrail."+name);
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public boolean hasDescription(){
        return !(this.description.isEmpty());
    }

    public boolean isWorldEnabled(Player player){
        if(disabledWorlds.isEmpty())
            return true;
        else {
            if(disabledWorlds.contains(player.getWorld()))
                return false;
            else
                return true;
        }
    }

}

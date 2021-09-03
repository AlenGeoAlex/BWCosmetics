package me.alen_alex.bwcosmetics.cosmetics.bowtrail;

import me.Abhigya.core.particle.particlelib.ParticleBuilder;
import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class BowTrial {

    private String name;
    private int cooldown;
    private List<ParticleBuilder> particleBuilders;

    public BowTrial(@NotNull String name,@NotNull List<String> particles,@NotNull int cooldown) {
        this.name = name;
        particles.forEach((particleEffect -> {
            this.particleBuilders.add(new ParticleBuilder(ParticleEffect.valueOf(particleEffect)));
        }));
        this.cooldown = cooldown;
    }

    public void display(Location location){
        Workload workload = () -> {
            particleBuilders.forEach((particleBuilder -> {
                particleBuilder.setLocation(location).display();
            }));
        };
        WorkloadScheduler.getSyncThread().add(workload);
    }

    public boolean hasPermission(Player player){
        return player.hasPermission("bwc.bowtrail."+name);
    }

    public int getCooldown() {
        return cooldown;
    }
}

package me.alen_alex.bwcosmetics.cosmetics;

import me.Abhigya.core.particle.particlelib.ParticleBuilder;
import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BowTrial {

    private String name;
    private ParticleEffect particle;
    private ParticleBuilder particleBuilder;

    public BowTrial(@NotNull String name,@NotNull ParticleEffect particle) {
        this.name = name;
        this.particle = particle;
        this.particleBuilder = new ParticleBuilder(this.particle);
    }

    public void display(Location location){
        Workload workload = () -> {
            particleBuilder.setLocation(location).display();
        };
        WorkloadScheduler.getSyncThread().add(workload);
    }


}

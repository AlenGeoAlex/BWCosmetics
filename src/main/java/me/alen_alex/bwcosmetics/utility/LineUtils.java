package me.alen_alex.bwcosmetics.utility;

import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LineUtils {

    //Call this in an async task and spawn the particle in a sync task
    public CompletableFuture<LinkedList<Location>> getLineLocations(Location locationA, Location locationB){
        CompletableFuture<LinkedList<Location>> locationList = new CompletableFuture<LinkedList<Location>>();
        Bukkit.getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                LinkedList<Location> locations = new LinkedList<Location>();
                final double spacing = 1.5D;
                Vector a = locationA.getDirection().normalize();
                Vector b = locationB.getDirection().normalize();
                Vector between = b.subtract(a);
                double length = between.length();
                between.normalize().multiply(spacing);
                double steps = length / spacing;
                for (int i = 0; i < steps; i++) {
                    Vector point = a.add(between);
                    locations.add(point.toLocation(locationA.getWorld()));
                }try {
                    locationList.complete(locations);
                }catch (Throwable e){
                    locationList.completeExceptionally(e);
                }
            }
        });
        return locationList;
    }

}

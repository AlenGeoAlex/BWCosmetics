package me.alen_alex.bwcosmetics.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    public static List<Location> getBlocksInRadius(Location baseLocation,int radius){
        final List<Location> inRadius = new ArrayList<>();
        int minX = baseLocation.getBlockX() - radius;
        int maxX = baseLocation.getBlockX() + radius;
        int minY = baseLocation.getBlockY() - radius;
        int maxY = baseLocation.getBlockY() + radius;
        int minZ = baseLocation.getBlockZ() - radius;
        int maxZ = baseLocation.getBlockZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    World world;
                    Location location = new Location(baseLocation.getWorld(), x, y, z);
                    if(location.getBlock().getType() != Material.BARRIER)
                        inRadius.add(location);
                }
            }
        }

        return inRadius;
    }

}

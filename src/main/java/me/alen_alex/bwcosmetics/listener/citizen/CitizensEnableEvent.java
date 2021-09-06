package me.alen_alex.bwcosmetics.listener.citizen;

import me.alen_alex.bwcosmetics.BWCosmetics;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensEnableEvent implements Listener {

    @EventHandler
    public void onCitizensEnableEvent(net.citizensnpcs.api.event.CitizensEnableEvent event){
        BWCosmetics plugin = BWCosmetics.getPlugin();
        if(plugin.getCacheManager().size() < 0){
            plugin.getLogger().info("No previous cache for npc has been found");
            plugin.getLogger().info("Continuing plugin initialization!");
            return;
        }
        plugin.getLogger().info("Found caches for NPC. Wiping it");
        if(plugin.isCitizensEnabled()){
            plugin.getCacheManager().fetchList().forEach((id) -> {
                NPC npc = plugin.getNpcRegistry().getById(id);
                npc.destroy();
            });
        }

    }

}

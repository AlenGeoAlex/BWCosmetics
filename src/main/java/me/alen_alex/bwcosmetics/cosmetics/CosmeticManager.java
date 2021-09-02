package me.alen_alex.bwcosmetics.cosmetics;

import de.leonhard.storage.Yaml;
import me.Abhigya.core.particle.particlelib.ParticleEffect;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.bowtrail.BowTrial;

import java.util.HashMap;

public class CosmeticManager {

    private HashMap<String, BowTrial> cachedBowTrial = new HashMap<String,BowTrial>();

    public void loadBowTrail(){
        Yaml config = BWCosmetics.getConfiguration().getBowTrailConfig();
        if(config != null){
            for(String keySet : config.keySet()){
                System.out.println(keySet);
                try {
                    cachedBowTrial.put(keySet,new BowTrial(config.getString(keySet+".name"), ParticleEffect.valueOf(keySet+".particle")));
                }catch (IllegalArgumentException e){
                    BWCosmetics.getPlugin().getLogger().warning("Disabling Bowtrail "+config.getString(keySet+".name")+" as it seems its impossible to fetch/load data");
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public HashMap<String, BowTrial> getCachedBowTrial() {
        return cachedBowTrial;
    }

    public boolean containsBowTrail(String name){
        return cachedBowTrial.containsKey(name);
    }


}

package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.FileUtils;

public class BowTrialConfig {

    private Yaml bowTrailConfig;

    public void generateConfig(){

        bowTrailConfig = BWCosmetics.getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("bowtrail.yml"),"bowtrail.yml","cosmetics");
        if(bowTrailConfig == null)
            BWCosmetics.getPlugin().getLogger().warning("Unable to create generate/load config for Bow trail!");
    }

    public Yaml getBowTrailConfig() {
        return bowTrailConfig;
    }
}

package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;

public class VictorydanceConfig {

     public static Yaml victorydanceConfig;

    public void generateConfig(){
        victorydanceConfig = BWCosmetics.getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("victorydance.yml"),"victorydance.yml","cosmetics");

    }
    public static Yaml getvictorydanceConfig(){
        return victorydanceConfig;
    }
}

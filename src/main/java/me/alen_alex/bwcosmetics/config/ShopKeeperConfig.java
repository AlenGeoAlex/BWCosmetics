package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;

public class ShopKeeperConfig {

    private Yaml shopkeeperConfig;

    public void generateConfig(){

        shopkeeperConfig = BWCosmetics.getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("shopkeepers.yml"),"shopkeepers.yml","cosmetics");
        if(shopkeeperConfig == null)
            BWCosmetics.getPlugin().getLogger().warning("Unable to create generate/load config for Bow trail!");
    }

    public Yaml getShopkeeperConfig() {
        return shopkeeperConfig;
    }

}

package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;

public class Configuration {

    private Config config;
    private String sqlHost,sqlUsername,sqlPassword,sqlPort,sqlDatabase,prefixMain;
    private boolean usingMysql,sslEnabled,cooldownMessagesEnabled;
    private boolean bowtrialEnabled,bowTrailEggEnabled,bowTrailFishhookEnabled,bowTrailArrowEnabled,bowTrailFireballEnabled,bowTrailSnowballEnabled;
    private boolean shopkeeperEnabled,shopkeeperCooldownEnabled,shopkeeperRandom,victoryDanceEnabled;
    private int shopKeeperCooldown;

    private BowTrialConfig bowTrialConfig;
    private ShopKeeperConfig shopKeeperConfig;
    private VictoryDanceConfig victoryDanceConfig;

    public boolean createConfiguration(BWCosmetics plugin){
        config = BWCosmetics.getPlugin().getFileUtils().createConfiguration(plugin);
        if(config != null){
            loadConfiguration();
            createOtherFiles();
            return true;
        }else
            return false;
    }

    private void createOtherFiles(){
        if(isBowtrialEnabled()){
            bowTrialConfig = new BowTrialConfig();
            bowTrialConfig.generateConfig();
        }

        if(isShopkeeperEnabled()){
            shopKeeperConfig = new ShopKeeperConfig();
            shopKeeperConfig.generateConfig();
        }

        if(isVictoryDanceEnabled()){
            victoryDanceConfig = new VictoryDanceConfig();
            victoryDanceConfig.generateConfig();
            victoryDanceConfig.loadVictoryDanceConfigurations();
        }
    }

    private void loadConfiguration(){
        usingMysql = config.getBoolean("server.use-mysql");
        sqlHost = config.getString("server.host");
        sqlPort = config.getString("server.port");
        sqlUsername = config.getString("server.username");
        sqlPassword = config.getString("server.password");
        sqlDatabase = config.getString("server.database");
        sslEnabled = config.getBoolean("server.usessl");
        bowtrialEnabled = config.getBoolean("cosmetics.bow-trial.enabled");
        bowTrailEggEnabled = config.getBoolean("cosmetics.bow-trial.entities-enabled.egg");
        bowTrailArrowEnabled = config.getBoolean("cosmetics.bow-trial.entities-enabled.arrow");
        bowTrailSnowballEnabled = config.getBoolean("cosmetics.bow-trial.entities-enabled.snowball");
        bowTrailFishhookEnabled = config.getBoolean("cosmetics.bow-trial.entities-enabled.fishhook");
        bowTrailFireballEnabled =  config.getBoolean("cosmetics.bow-trial.entities-enabled.fireball");
        shopkeeperEnabled = config.getBoolean("cosmetics.shopkeeper.enabled");
        shopkeeperCooldownEnabled = config.getBoolean("cosmetics.shopkeeper.cooldown.enabled");
        shopKeeperCooldown = config.getInt("cosmetics.shopkeeper.cooldown.cooldownInSecs");
        shopkeeperRandom = config.getBoolean("cosmetics.shopkeeper.randomize-player-selection-each-time");
        victoryDanceEnabled = config.getBoolean("cosmetics.victorydance.enabled");
        cooldownMessagesEnabled = config.getBoolean("settings.showCooldownmessages");
    }

    public Config getConfig() {
        return config;
    }

    public String getSqlHost() {
        return sqlHost;
    }

    public String getSqlUsername() {
        return sqlUsername;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public String getSqlPort() {
        return sqlPort;
    }

    public String getSqlDatabase() {
        return sqlDatabase;
    }

    public String getPrefixMain() {
        return prefixMain;
    }

    public boolean isUsingMysql() {
        return usingMysql;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public boolean isBowtrialEnabled() {
        return bowtrialEnabled;
    }

    public Yaml getBowTrailConfig(){
        return bowTrialConfig.getBowTrailConfig();
    }

    public boolean isBowTrailEggEnabled() {
        return bowTrailEggEnabled;
    }

    public boolean isBowTrailFishhookEnabled() {
        return bowTrailFishhookEnabled;
    }

    public boolean isBowTrailArrowEnabled() {
        return bowTrailArrowEnabled;
    }

    public boolean isBowTrailFireballEnabled() {
        return bowTrailFireballEnabled;
    }

    public boolean isBowTrailSnowballEnabled() {
        return bowTrailSnowballEnabled;
    }

    public boolean isShopkeeperEnabled() {
        return shopkeeperEnabled;
    }

    public boolean isShopkeeperCooldownEnabled() {
        return shopkeeperCooldownEnabled;
    }

    public int getShopKeeperCooldown() {
        return shopKeeperCooldown;
    }

    public Yaml getShopKeeperConfig() {
        return shopKeeperConfig.getShopkeeperConfig();
    }

    public boolean isShopkeeperRandom() {
        return shopkeeperRandom;
    }

    public boolean isVictoryDanceEnabled() {
        return victoryDanceEnabled;
    }

    public boolean isCooldownMessagesEnabled() {
        return cooldownMessagesEnabled;
    }

    public VictoryDanceConfig getVictoryDanceConfig() {
        return victoryDanceConfig;
    }
}

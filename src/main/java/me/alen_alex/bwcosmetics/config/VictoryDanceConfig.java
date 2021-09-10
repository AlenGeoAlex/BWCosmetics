package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.MessageUtils;

import java.util.List;

public class VictoryDanceConfig {

    private Yaml victorydanceConfig;
    private boolean witherEnabled,dragonRideEnabled,breakEnviormentEnabled,dragonRideTeleportOutsideWorldBorder;
    private List<String> witherDisabledWorlds,witherMessageTips,dragonRideDisabled;
    private String witherName,witherMessageStart,witherMessageStop,dragonName;
    private int witherCooldownInMins,witherRefreshRate,dragonCooldownInMins,dragonRefreshRate;
    private double witherVelocityMultiplier;

    public void generateConfig(){
        victorydanceConfig = BWCosmetics.getPlugin().getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("victorydances.yml"),"victorydance.yml","cosmetics");
    }

    public void loadVictoryDanceConfigurations(){
        witherEnabled = victorydanceConfig.getBoolean("Witherride.enabled");
        witherDisabledWorlds = victorydanceConfig.getStringList("Witherride.disabled-worlds");
        witherName = MessageUtils.parseColor(victorydanceConfig.getString("Witherride.wither-name"));
        witherCooldownInMins = victorydanceConfig.getInt("Witherride.cooldownInMins");
        witherMessageStart = MessageUtils.parseColor(victorydanceConfig.getString("Witherride.messages.start-message"));
        witherMessageStop = MessageUtils.parseColor(victorydanceConfig.getString("Witherride.messages.stop-message"));
        witherRefreshRate = victorydanceConfig.getInt("Witherride.settings.witherRefreshRateInTicks");
        witherVelocityMultiplier = victorydanceConfig.getDouble("Witherride.settings.witherVelocityMultiplier");
        dragonRideEnabled = victorydanceConfig.getBoolean("Dragonride.enabled");
        dragonName = MessageUtils.parseColor(victorydanceConfig.getString("Dragonride.dragon-name"));
        dragonRideDisabled = victorydanceConfig.getStringList("Dragonride.disabled-worlds");
        dragonCooldownInMins = victorydanceConfig.getInt("Dragonride.cooldownInMins");
        breakEnviormentEnabled = victorydanceConfig.getBoolean("Dragonride.settings.break-blocks-in-world");
        dragonRefreshRate = victorydanceConfig.getInt("Dragonride.settings.DragonRefreshRateInTicks");
        dragonRideTeleportOutsideWorldBorder = victorydanceConfig.getBoolean("Dragonride.settings.teleport-to-centre-when-outside-worldborder");
        victorydanceConfig = null;
    }

    public boolean isWitherEnabled() {
        return witherEnabled;
    }

    public List<String> getWitherDisabledWorlds() {
        return witherDisabledWorlds;
    }

    public List<String> getWitherMessageTips() {
        return witherMessageTips;
    }

    public String getWitherName(String name) {
        return witherName.replaceAll("%player%",name);
    }

    public String getWitherMessageStart() {
        return witherMessageStart;
    }

    public String getWitherMessageStop() {
        return witherMessageStop;
    }

    public int getWitherCooldownInMins() {
        return witherCooldownInMins;
    }

    public int getWitherRefreshRate() {
        return witherRefreshRate;
    }

    public double getWitherVelocityMultiplier() {
        return witherVelocityMultiplier;
    }

    public boolean isDragonRideEnabled() {
        return dragonRideEnabled;
    }

    public boolean isBreakEnviormentEnabled() {
        return breakEnviormentEnabled;
    }

    public List<String> getDragonRideDisabled() {
        return dragonRideDisabled;
    }

    public int getDragonCooldownInMins() {
        return dragonCooldownInMins;
    }

    public int getDragonRefreshRate() {
        return dragonRefreshRate;
    }

    public String getDragonName(String name) {
        return dragonName.replaceAll("%player%",name);
    }

    public boolean isDragonRideTeleportOutsideWorldBorder() {
        return dragonRideTeleportOutsideWorldBorder;
    }
}

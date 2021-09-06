package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import me.alen_alex.bwcosmetics.BWCosmetics;

import java.util.List;

public class VictoryDanceConfig {

    private Yaml victorydanceConfig;
    private boolean witherEnabled;
    private List<String> disabledWorlds,witherMessageTips;
    private String witherName,witherMessageStart,witherMessageStop;
    private int witherCooldownInMins;

    public void generateConfig(){
        victorydanceConfig = BWCosmetics.getPlugin().getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("victorydance.yml"),"victorydance.yml","cosmetics");
    }

    public void loadVictoryDanceConfigurations(){
        witherEnabled = victorydanceConfig.getBoolean("Witherride.enabled");
        disabledWorlds = victorydanceConfig.getStringList("Witherride.disabled-worlds");
        witherName = victorydanceConfig.getString("Witherride.wither-name");
        witherCooldownInMins = victorydanceConfig.getInt("Witherride.cooldownInMins");
        witherMessageStart = victorydanceConfig.getString("Witherride.messages.start-message");
        witherMessageStop = victorydanceConfig.getString("Witherride.messages.stop-message");

        victorydanceConfig = null;
    }

    public Yaml getVictorydanceConfig() {
        return victorydanceConfig;
    }

    public boolean isWitherEnabled() {
        return witherEnabled;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public List<String> getWitherMessageTips() {
        return witherMessageTips;
    }

    public String getWitherName() {
        return witherName;
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
}

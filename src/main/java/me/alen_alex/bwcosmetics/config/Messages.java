package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Yaml;
import jdk.nashorn.internal.objects.annotations.Getter;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.MessageUtils;
import me.alen_alex.bwcosmetics.utility.TimeUtils;

public class Messages {

    private Yaml messageConfig;
    private String disabledWorldVictoryDance,disabledWorldBowTrial;
    private String inCooldownPL;

    public boolean generateMessages(){
        messageConfig = BWCosmetics.getPlugin().getFileUtils().createFile(BWCosmetics.getPlugin(),BWCosmetics.getPlugin().getResource("messages.yml"),"messages.yml","language");
        if(messageConfig == null) {
            BWCosmetics.getPlugin().getLogger().warning("Unable to create generate/load config for messages!");
            return false;
        }
        else{
            loadMessages();
            return true;
        }
    }

    private void loadMessages(){
        disabledWorldVictoryDance = MessageUtils.parseColor(messageConfig.getString("messages.error.disabled-world-victoryDance"));
        disabledWorldBowTrial = MessageUtils.parseColor(messageConfig.getString("messages.error.disabled-world-bowTrail"));
        inCooldownPL = MessageUtils.parseColor(messageConfig.getString("messages.error.inCooldown"));
        messageConfig = null;
    }

    @Getter
    public String getDisabledWorldBowTrial() {
        return disabledWorldBowTrial;
    }

    @Getter
    public String getDisabledWorldVictoryDance() {
        return disabledWorldVictoryDance;
    }

    @Getter
    public String getInCooldown(long timeStamp) {
        return inCooldownPL.replaceAll("%remaining%", String.valueOf(TimeUtils.getSecondsFromLongTime(timeStamp)));
    }

    @Getter
    @Deprecated
    public String getInCooldown() {
        return inCooldownPL;
    }

}

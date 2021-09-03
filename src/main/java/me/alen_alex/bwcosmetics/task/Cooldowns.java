package me.alen_alex.bwcosmetics.task;

import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Cooldowns {

    private HashMap<UUID,Long> bowTrialCooldown = new HashMap<UUID,Long>();

    public boolean bowTrialContains(UUID uuid){
        return bowTrialCooldown.containsKey(uuid);
    }

    public void addToBowCooldown(UUID uuid,long timestamp){
        bowTrialCooldown.put(uuid,timestamp);
    }

    public void addToBowCooldown(UUID uuid,int seconds){
        addToBowCooldown(uuid,(System.currentTimeMillis() * 1000 * seconds ));
    }

    public void runCooldownTask(){
        Workload bowTrial = () -> {
            if(!bowTrialCooldown.isEmpty()){
                bowTrialCooldown.entrySet().removeIf(cooldownData -> System.currentTimeMillis() >= cooldownData.getValue());
                /*while (bowtrail.hasNext()){
                    Map.Entry<UUID,Long> cooldownData = bowtrail.next();
                    if(System.currentTimeMillis() >= cooldownData.getValue())
                        bowtrail.remove();
                }*/
            }
        };

        Workload nextCooldown = () -> {

        };

        WorkloadScheduler.getAsyncThread().add(bowTrial);
        WorkloadScheduler.getAsyncThread().add(nextCooldown);

    }


}

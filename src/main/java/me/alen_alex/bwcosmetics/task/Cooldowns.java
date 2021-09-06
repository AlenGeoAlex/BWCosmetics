package me.alen_alex.bwcosmetics.task;

import me.Abhigya.core.util.tasks.Workload;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.Debug;
import me.alen_alex.bwcosmetics.utility.WorkloadScheduler;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Cooldowns extends BukkitRunnable {

    private HashMap<UUID,Long> bowTrialCooldown = new HashMap<UUID,Long>();

    private HashMap<UUID,Long> shopkeeperskin = new HashMap<UUID,Long>();

    public boolean bowTrialContains(UUID uuid){
        return bowTrialCooldown.containsKey(uuid);
    }

    public void addToBowCooldown(UUID uuid,long timestamp){
        bowTrialCooldown.put(uuid,timestamp);
    }

    public void addToBowCooldown(UUID uuid,int seconds){
        addToBowCooldown(uuid,(System.currentTimeMillis()+((long) 1000 * seconds)));
    }

    public boolean shopKeeperCooldownContains(UUID uuid){
        return shopkeeperskin.containsKey(uuid);
    }

    public void addToShopKeeperCooldown(UUID uuid,long timestamp){
        shopkeeperskin.put(uuid,timestamp);
    }

    public void addToShopKeeperCooldown(UUID uuid,int seconds){
        addToShopKeeperCooldown(uuid,(System.currentTimeMillis()+((long) 1000 * seconds)));
    }



    /*public void runCooldownTask(){
        System.out.println("Triggered Cooldown Task");
        Workload bowTrial = () -> {
            System.out.println("Empty");
            if(!bowTrialCooldown.isEmpty()){
                System.out.println("Is not empty");
                Iterator<Map.Entry<UUID,Long>> bowtrail = bowTrialCooldown.entrySet().iterator();
                while (bowtrail.hasNext()){
                    Map.Entry<UUID,Long> cooldownData = bowtrail.next();
                    System.out.println(System.currentTimeMillis()+" >= "+ cooldownData.getValue());
                    if(System.currentTimeMillis() >= cooldownData.getValue()) {
                        bowtrail.remove();
                        System.out.println("Removed");
                    }
                }
            }
        };


        BWCosmetics.getScheduler().getAsyncThread().add(bowTrial);
        //WorkloadScheduler.getAsyncThread().add(nextCooldown);
    }*/

    @Override
    public void run() {
        if(!bowTrialCooldown.isEmpty()) {
            Iterator<Map.Entry<UUID, Long>> bowtrail = bowTrialCooldown.entrySet().iterator();
            while (bowtrail.hasNext()) {
                Map.Entry<UUID, Long> cooldownData = bowtrail.next();
                if (System.currentTimeMillis() >= cooldownData.getValue()) {
                    bowtrail.remove();
                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Removed "+cooldownData.getKey()+" from bowtrail's cooldown!");
                }
            }
        }
    }
}

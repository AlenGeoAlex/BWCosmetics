package me.alen_alex.bwcosmetics.utility;

import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.Abhigya.core.util.tasks.WorkloadDistributor;
import me.Abhigya.core.util.tasks.WorkloadThread;
import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.scheduler.BukkitTask;

public class WorkloadScheduler {


    private  final static WorkloadDistributor distributor = new WorkloadDistributor();
    private static WorkloadThread syncThread,asyncThread;
    private static BukkitTask syncTask,asyncTask;
    public void intializeThread() {
        syncThread = distributor.createThread(20000000L);
        asyncThread = distributor.createThread(10000000L);
        syncTask = SchedulerUtils.runTaskTimer(syncThread, 1L, 1L, BWCosmetics.getPlugin());
        asyncTask = SchedulerUtils.runTaskTimerAsynchronously(asyncThread,1L,1L,BWCosmetics.getPlugin());
    }


    public WorkloadThread getSyncThread() {
        return syncThread;
    }

    public WorkloadThread getAsyncThread() {
        return asyncThread;
    }

}

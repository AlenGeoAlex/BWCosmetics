package me.alen_alex.bwcosmetics.utility;

import me.alen_alex.bwcosmetics.BWCosmetics;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Debug {

    public enum DebugType {
        NORMAL,
        WARN,
        ERROR
    }

    private static boolean debuggingEnabled = true;

    public static void setDebugMessage(DebugType debugType,String className,String message){
        BWCosmetics.getPlugin().getServer().getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(debuggingEnabled) {
                    if (message.isEmpty())
                        return;
                    System.out.println("[" + debugType.name() + "] - " + className + " - " + message);
                }
            }
        });
    }

    public static void setDebugMessage(String message){
        BWCosmetics.getPlugin().getServer().getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(debuggingEnabled) {
                    if (message.isEmpty())
                        return;
                    System.out.println("[N/A] - N/A - " + message);
                }
            }
        });
    }

    public static void setDebugMessage(DebugType debugType,String message){
        BWCosmetics.getPlugin().getServer().getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(debuggingEnabled) {
                    if (message.isEmpty())
                        return;
                    System.out.println("[" + debugType.name() + "] - N/A - " + message);
                }
            }
        });
    }

}

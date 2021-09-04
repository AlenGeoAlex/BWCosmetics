package me.alen_alex.bwcosmetics.utility;

import me.alen_alex.bwcosmetics.BWCosmetics;

import java.util.Random;

public class RandomUtility {

    private Random randomInstance;
    private BWCosmetics plugin;

    public RandomUtility(BWCosmetics plugin) {
        this.randomInstance = new Random(System.currentTimeMillis());
        this.plugin = plugin;
    }

    public Random getRandomInstance() {
        return randomInstance;
    }

    public int getRandomNumber(int count){
        return randomInstance.nextInt(count);
    }

    public int refreshAndGetRandom(int count){
        randomInstance.setSeed(System.currentTimeMillis());
        return randomInstance.nextInt(count);
    }

}

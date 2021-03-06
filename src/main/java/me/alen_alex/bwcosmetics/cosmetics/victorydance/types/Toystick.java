package me.alen_alex.bwcosmetics.cosmetics.victorydance.types;


import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDance;
import me.alen_alex.bwcosmetics.cosmetics.victorydance.VictoryDanceType;
import me.alen_alex.bwcosmetics.utility.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Toystick extends VictoryDance implements Listener {
    public Toystick(Plugin plugin, Player player,Location location){
        super(plugin,player,location, VictoryDanceType.TOYSTICK);
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public void startdance(){

        if(!hasUsePermission())
            return;

            getPlayer().getInventory().setItemInHand(givestick(getPlayer()));
            World world = getPlayer().getWorld();
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(getPlayer().getWorld() != world){
                        this.cancel();
                    }
                }
            }.runTaskTimer(getPlugin(),1L,20L);
            return;

    }

    @EventHandler
    public void onclick(PlayerInteractEvent e){
        if(e.getPlayer().getItemInHand() != givestick(e.getPlayer()) &&
                e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != givestick(e.getPlayer()).getItemMeta().getDisplayName() ||
                e.getAction() == Action.LEFT_CLICK_BLOCK  || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = e.getClickedBlock();
            List<Block> blocks = getBlocks(block.getLocation(),6,false);
            for(Block block1 : blocks){
                throwblock(block1);
            }
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            e.getPlayer().setVelocity(new Vector(0, 3, -1));
        }
    }
    public ItemStack givestick(Player player){
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Right click to use");
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public List<Block> getBlocks(Location location, int radius,boolean hollow){
        List<Block> blocks = new ArrayList<Block>();
        List<Location>  locations= BlockUtils.getLocationInRadius(location,radius);
        for(Location location1 : locations){
            blocks.add(location1.getBlock());
        }
        return blocks;
    }
    @SuppressWarnings("Depricated")
    public void throwblock(Block block){
        if(block == null || block.getType() == Material.AIR){
            return;
        }
        if(block.getType() == Material.TNT){
            TNTPrimed tnt= block.getWorld().spawn(block.getLocation().add(0.0, 1.0, 0.0), TNTPrimed.class);
            float x = -1.0f + (float)(Math.random() * 3.0);
            float y = 0.5f;
            float z = -0.3f + (float)(Math.random() * 1.6);
            tnt.setVelocity(new Vector(x,y,z));
        }
        else{
            FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.0d,1.0d,0.0d),block.getType(),block.getData());
            fallingBlock.setDropItem(false);
            float x = -1.0f + (float)(Math.random() * 3.0);
            float y = 0.5f;
            float z = -0.3f + (float)(Math.random() * 1.6);
            fallingBlock.setVelocity(new Vector(x,y,z));
        }
        block.setType(Material.AIR);
    }
}

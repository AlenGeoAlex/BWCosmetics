package me.alen_alex.bwcosmetics.cosmetics.shopkeeper;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import es.eltrueno.npc.TruenoNPC;
import es.eltrueno.npc.TruenoNPCApi;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.SkinType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Shopkeeper {

    private HashMap<ITeam,Location> shopNPC = new HashMap<>();
    private HashMap<ITeam,Location> upgradableShop = new HashMap<>();
    private HashMap<ITeam, Player> playerSelected= new HashMap<ITeam, Player>();
    private IArena arena;
    private List<ITeam> teams;
    private BWCosmetics plugin;
    private List<Object> npcs;
    public Shopkeeper(BWCosmetics plugin,  IArena arena) {
        this.plugin = plugin;
        this.arena = arena;
        this.teams = arena.getTeams();
        teams.forEach((iTeam -> {
            shopNPC.put(iTeam,iTeam.getShop());
            upgradableShop.put(iTeam,iTeam.getTeamUpgrades());
        }));
    }

    public HashMap<ITeam, Location> getShopNPC() {
        return shopNPC;
    }

    public HashMap<ITeam, Location> getUpgradableShop() {
        return upgradableShop;
    }

    public IArena getArena() {
        return arena;
    }

    public List<ITeam> getTeams() {
        return teams;
    }

    public void selectRandomPlayer() {
        plugin.getServer().getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                teams.forEach((iTeam -> {
                    List<Player> hasShopkeeperskins = new ArrayList<>();
                    for (Player player : iTeam.getMembers()) {
                        if (BWCosmetics.getPlayerManager().getPlayer(player).hasShopKeeper() && !(BWCosmetics.getCooldownTasks().shopKeeperCooldownContains(player.getUniqueId())))
                            hasShopkeeperskins.add(player);
                    }

                    if (!hasShopkeeperskins.isEmpty()) {
                        if (BWCosmetics.getConfiguration().isShopkeeperRandom())
                            playerSelected.put(iTeam,hasShopkeeperskins.get(BWCosmetics.getRandomUtility().refreshAndGetRandom(hasShopkeeperskins.size())));
                        else
                            playerSelected.put(iTeam,hasShopkeeperskins.get(0));
                    }
                }));
            }
        });
    }



    public void spawnShopkeepers(){
        Iterator<Map.Entry<ITeam,Location>> shopNPCMAP = shopNPC.entrySet().iterator();
        while (shopNPCMAP.hasNext()){
            Map.Entry<ITeam,Location> shopEntity = shopNPCMAP.next();
            if(playerSelected.containsKey(shopEntity.getKey())){
                List<Entity> entitiesNearTeam = new ArrayList<>(shopEntity.getValue().getWorld().getNearbyEntities(shopEntity.getValue(),3,3,3));
                if(!entitiesNearTeam.isEmpty()) {
                    for (Entity entity : entitiesNearTeam) {
                        if(entity.getLocation().distance(shopEntity.getValue()) <= 0.2D) {
                            if( BWCosmetics.getPlayerManager().contains(playerSelected.get(shopEntity.getKey()).getUniqueId()) ){
                                entity.remove();
                                if(BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(shopEntity.getKey())).getPlayerShopkeeper().getSkinType() == SkinType.PLAYERSKIN){
                                    TruenoNPC npc = TruenoNPCApi.createNPC(plugin,shopNPC.get(playerSelected.get(shopEntity.getKey())),BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(shopEntity.getKey())).getPlayerShopkeeper().getNpcSkin());
                                    npcs.add(npc);
                                }else{
                                    LivingEntity spawnableEntity = (LivingEntity) shopNPC.get(playerSelected.get(shopEntity.getKey())).getWorld().spawnEntity( shopNPC.get(playerSelected.get(shopEntity.getKey())), BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(shopEntity.getKey())).getPlayerShopkeeper().getEntityType() );
                                    npcs.add(spawnableEntity);
                                }
                            }
                        }
                    }
                }
            }
        }

        Iterator<Map.Entry<ITeam,Location>> upgradeNPCMAP = upgradableShop.entrySet().iterator();
        while (upgradeNPCMAP.hasNext()){
            Map.Entry<ITeam,Location> upgNPC = upgradeNPCMAP.next();
            if(playerSelected.containsKey(upgNPC.getKey())){
                List<Entity> entitiesNearTeam = new ArrayList<>(upgNPC.getValue().getWorld().getNearbyEntities(upgNPC.getValue(),3,3,3));
                if(!entitiesNearTeam.isEmpty()) {
                    for (Entity entity : entitiesNearTeam) {
                        if(entity.getLocation().distance(upgNPC.getValue()) <= 0.2D) {
                            if( BWCosmetics.getPlayerManager().contains(playerSelected.get(upgNPC.getKey()).getUniqueId()) ){
                                entity.remove();
                                if(BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(upgNPC.getKey())).getPlayerShopkeeper().getSkinType() == SkinType.PLAYERSKIN){
                                    TruenoNPC npc = TruenoNPCApi.createNPC(plugin,upgradableShop.get(playerSelected.get(upgNPC.getKey())),BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(upgNPC.getKey())).getPlayerShopkeeper().getNpcSkin());
                                    npcs.add(npc);
                                }else{
                                    LivingEntity spawnableEntity = (LivingEntity) upgradableShop.get(playerSelected.get(upgNPC.getKey())).getWorld().spawnEntity( upgradableShop.get(playerSelected.get(upgNPC.getKey())), BWCosmetics.getPlayerManager().getPlayer(playerSelected.get(upgNPC.getKey())).getPlayerShopkeeper().getEntityType() );
                                    npcs.add(spawnableEntity);
                                }
                            }
                        }
                    }
                }
            }
        }


    }

}

package me.alen_alex.bwcosmetics.cosmetics.shopkeeper;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.Debug;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class Shopkeeper {

    private HashMap<ITeam,Location> shopNPC = new HashMap<>();
    private HashMap<ITeam,Location> upgradableShop = new HashMap<>();
    private HashMap<ITeam, Player> playerSelected= new HashMap<ITeam, Player>();
    private IArena arena;
    private List<ITeam> teams;
    private BWCosmetics plugin;
    private List<NPC> npcs = new ArrayList<>();
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
        teams.forEach((iTeam -> {
            List<Player> hasShopkeeperskins = new ArrayList<>();
            for (Player player : iTeam.getMembers()) {
                if (plugin.getPlayerManager().getPlayer(player).hasShopKeeper() && !(plugin.getCooldownTasks().shopKeeperCooldownContains(player.getUniqueId()))) {
                    if(BWCosmetics.getPlugin().getPlayerManager().getPlayer(player).getPlayerShopkeeper().hasPermission(player) && player.hasPermission("bwc.use.shopkeeper")) {
                        hasShopkeeperskins.add(player);
                        Debug.setDebugMessage(Debug.DebugType.NORMAL, this.getClass().getSimpleName(),"Player "+player.getName()+" has permission and not in cooldown!! Added to search list");
                    }
                }
            }

            if (!hasShopkeeperskins.isEmpty()) {
                if (BWCosmetics.getPlugin().getConfiguration().isShopkeeperRandom()) {
                    Player player = hasShopkeeperskins.get(plugin.getRandomUtility().refreshAndGetRandom(hasShopkeeperskins.size()));
                    playerSelected.put(iTeam, player);
                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Skin for team "+iTeam.getName()+" has been selected as "+player.getName());
                }else {
                    playerSelected.put(iTeam, hasShopkeeperskins.get(0));
                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Skin for team "+iTeam.getName()+" has been selected as "+hasShopkeeperskins.get(0).getName());
                }
            }
        }));

    }



    public void spawnShopkeepers(){
        Iterator<Map.Entry<ITeam,Location>> shopNPCMAP = shopNPC.entrySet().iterator();
        while (shopNPCMAP.hasNext()){
            Map.Entry<ITeam,Location> shopEntity = shopNPCMAP.next();
            boolean spawned = false;
            if(playerSelected.containsKey(shopEntity.getKey())){
                List<Entity> entitiesNearTeam = shopEntity.getValue().getWorld().getEntities();
                if(!entitiesNearTeam.isEmpty()) {
                    for (Entity entity : entitiesNearTeam) {
                        if(entity.getLocation().distance(shopEntity.getValue()) <= 1D) {
                            if (!spawned) {
                                Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Spawning of NPC[Shopkeeper] will be continued since no NPC has been yet spawned!");
                                if (BWCosmetics.getPlugin().getPlayerManager().contains(playerSelected.get(shopEntity.getKey()).getUniqueId())) {
                                    final Location entityLocation = entity.getLocation();
                                    final Player playerSorted = playerSelected.get(shopEntity.getKey());
                                    final ITeam playerTeam = shopEntity.getKey();
                                    entity.remove();
                                    npcs.add(spawnNPC(entityLocation,plugin.getPlayerManager().getPlayer(playerSorted).getPlayerShopkeeper()));
                                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Shop NPC for the player "+playerSorted.getName()+" is "+plugin.getPlayerManager().getPlayer(playerSorted).getPlayerShopkeeper().getSkinType().name());
                                    spawned = true;
                                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Shop NPC for the player "+playerSorted.getName()+" has been spawned for the team "+playerTeam.getName());
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
            boolean spawned = false;
            if(playerSelected.containsKey(upgNPC.getKey())){
                List<Entity> entitiesNearTeam = upgNPC.getValue().getWorld().getEntities();
                if(!entitiesNearTeam.isEmpty()) {
                    for (Entity entity : entitiesNearTeam) {
                        if(entity.getLocation().distance(upgNPC.getValue()) <= 1D) {
                            if (!spawned) {
                                Debug.setDebugMessage(Debug.DebugType.NORMAL, this.getClass().getSimpleName(), "Spawning of NPC[Upgrade] will be continued since no NPC has been yet spawned!");
                                if (BWCosmetics.getPlugin().getPlayerManager().contains(playerSelected.get(upgNPC.getKey()).getUniqueId())) {
                                    final Location entityLocation = entity.getLocation();
                                    final Player playerSorted = playerSelected.get(upgNPC.getKey());
                                    final ITeam playerTeam = upgNPC.getKey();
                                    entity.remove();
                                    npcs.add(spawnNPC(entityLocation, plugin.getPlayerManager().getPlayer(playerSorted).getPlayerShopkeeper()));
                                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Upgrade NPC for the player "+playerSorted.getName()+" is "+plugin.getPlayerManager().getPlayer(playerSorted).getPlayerShopkeeper().getSkinType().name());
                                    spawned = true;
                                    Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"Upgrade NPC for the player "+playerSorted.getName()+" has been spawned for the team "+playerTeam.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeSpawnedNPC(){
        if(npcs.isEmpty())
            return;

        npcs.forEach((npc) -> {
            npc.destroy();
            plugin.getCacheManager().removeFromCache(npc.getId());
            Debug.setDebugMessage(Debug.DebugType.NORMAL,this.getClass().getSimpleName(),"NPC with "+npc.getId()+" has beem removed from cache!");
        });
    }

    //TODO ADD A WATCHDOG.JSON
    private NPC spawnNPC(Location location,ShopkeeperSkins skinData){
        NPC npc = null;
        if(skinData.getSkinType() == SkinType.PLAYERSKIN){
            npc = plugin.getNpcRegistry().createNPC(EntityType.PLAYER,"");
            npc.getOrAddTrait(SkinTrait.class).setSkinPersistent(skinData.getName(),skinData.getSkinSignature(),skinData.getSkinTexture());
        }else if(skinData.getSkinType() == SkinType.ENTITY)
            npc = plugin.getNpcRegistry().createNPC(skinData.getEntityType(),"");
        else
            npc = plugin.getNpcRegistry().createNPC(EntityType.VILLAGER,"");
        if(npc != null){
            npc.setUseMinecraftAI(false);
            npc.setFlyable(false);
            npc.setProtected(true);
            npc.setAlwaysUseNameHologram(false);
            npc.spawn(location);
            npc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);
            npc.faceLocation(location);
            plugin.getCacheManager().addToCache(npc.getId());
        }
        return npc;
    }


}

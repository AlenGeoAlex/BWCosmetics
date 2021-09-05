package me.alen_alex.bwcosmetics.playerdata;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.shopkeeper.ShopkeeperSkins;
import me.alen_alex.bwcosmetics.cosmetics.bowtrail.BowTrial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerCosmetic {

    private UUID playerUUID;
    private Player player;
    private BowTrial playerBowTrail;
    private ShopkeeperSkins playerShopkeeper;

    public PlayerCosmetic(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.player = Bukkit.getPlayer(playerUUID);
        playerBowTrail = null;
        playerShopkeeper = null;
        loadCosmetics();
    }

    public void loadCosmetics(){
        Bukkit.getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                ResultSet set = BWCosmetics.getPlugin().getStorage().getUserData(playerUUID);
                try {
                    if(set.next()) {
                        if(set.getString("bowtrial") != null) {
                            String bowTrial = set.getString("bowtrial");
                            if(!bowTrial.isEmpty()) {
                                if (BWCosmetics.getPlugin().getCosmeticManager().containsBowTrail(bowTrial))
                                    playerBowTrail = BWCosmetics.getPlugin().getCosmeticManager().getCachedBowTrial().get(bowTrial);
                            }
                        }

                        if(set.getString("shopkeeper") != null){
                            String shopkeeper = set.getString("shopkeeper");
                            if(!shopkeeper.isEmpty()){
                                if(BWCosmetics.getPlugin().getCosmeticManager().containsShopkeeperskin(shopkeeper)){
                                    playerShopkeeper = BWCosmetics.getPlugin().getCosmeticManager().getCachedSkins().get(shopkeeper);
                                 }
                            }
                        }
                    }
                    set.getStatement().close();
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BowTrial getPlayerBowTrail() {
        return playerBowTrail;
    }

    public void setPlayerBowTrail(BowTrial playerBowTrail) {
        this.playerBowTrail = playerBowTrail;
    }

    public boolean hasBowTrail(){
        return this.playerBowTrail != null;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public ShopkeeperSkins getPlayerShopkeeper() {
        return playerShopkeeper;
    }

    public void setPlayerShopkeeper(ShopkeeperSkins playerShopkeeper) {
        this.playerShopkeeper = playerShopkeeper;
    }

    public boolean hasShopKeeper(){
        return this.playerShopkeeper != null;
    }

    public void save(){
        BWCosmetics.getPlugin().getStorage().saveUserData(this);
    }

    public void destroy(){
        try {
            this.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}

package me.alen_alex.bwcosmetics.playerdata;

import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.cosmetics.CosmeticManager;
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

    public PlayerCosmetic(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.player = Bukkit.getPlayer(playerUUID);
        playerBowTrail = null;
        loadCosmetics();
    }

    public void loadCosmetics(){
        Bukkit.getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                ResultSet set = BWCosmetics.getStorage().getUserData(playerUUID);
                try {
                    if(set.next()) {
                        if(!set.getString("bowtrial").isEmpty()) {
                            String bowTrial = set.getString("bowtrial");
                            if(BWCosmetics.getCosmeticManager().containsBowTrail(bowTrial))
                                playerBowTrail = BWCosmetics.getCosmeticManager().getCachedBowTrial().get(bowTrial);
                        }
                    }
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
}

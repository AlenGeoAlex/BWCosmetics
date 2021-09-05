package me.alen_alex.bwcosmetics.data;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.Abhigya.core.database.sql.sqlite.SQLite;
import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.playerdata.PlayerCosmetic;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Storage {

    private boolean mysqlEnabled;
    private SQLDatabase sqlDatabase;
    private SQL SQL;
    private BWCosmetics plugin;

    public Storage(BWCosmetics plugin, boolean mysqlEnabled) {
        this.mysqlEnabled = mysqlEnabled;
        this.plugin = plugin;
    }

    public void build(){
        if(mysqlEnabled){
            this.sqlDatabase = new HikariClientBuilder("jdbc:mysql://"+BWCosmetics.getPlugin().getConfiguration().getSqlHost()+":"+BWCosmetics.getPlugin().getConfiguration().getSqlPort()+"/"+BWCosmetics.getPlugin().getConfiguration().getSqlDatabase(),BWCosmetics.getPlugin().getConfiguration().getSqlUsername(),BWCosmetics.getPlugin().getConfiguration().getSqlPassword(),true).setMaximumPoolSize(10).build();
            try {
                sqlDatabase.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            this.sqlDatabase = new SQLite(plugin, new File(plugin.getDataFolder(),"database.db"),true);
            try {
                sqlDatabase.connect();
                System.out.println(sqlDatabase.getConnection().getAutoCommit());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tryConnection(){
        try {
            this.SQL = new SQL(this.sqlDatabase.getConnection());
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public SQL getSQL() {
        try {
            if(this.SQL == null){
                tryConnection();
            }else if(this.SQL.getConnection().isClosed()){
                tryConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SQL;
    }

    public boolean isConnectionOnline(){
        return sqlDatabase.isConnected();
    }

    public void createDatabase(){
        if(isConnectionOnline()) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        PreparedStatement ps = SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `cosmetics` (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,`uuid` VARCHAR(50) NOT NULL,`death` VARCHAR(30),`killeffect` VARCHAR(30), `victorydance` VARCHAR(30),`bowtrial` VARCHAR(30),`shopkeeper`  VARCHAR(30));");
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public ResultSet getUserData(UUID uuid){
        if(!isConnectionOnline()){
            return null;
        }
        try {
            PreparedStatement ps = this.SQL.getConnection().prepareStatement("SELECT * FROM `cosmetics` WHERE `uuid` = '"+uuid.toString()+"';");
            ResultSet set = ps.executeQuery();
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean doUserExist(UUID uuid){
        if(!isConnectionOnline()){
            return false;
        }
        try {
            PreparedStatement ps = this.SQL.getConnection().prepareStatement("SELECT `id` FROM `cosmetics` WHERE `uuid` = '"+uuid.toString()+"';");
            ResultSet set = ps.executeQuery();
            if(set.next()) {
                ps.close();
                set.close();
                return true;
            } else {
                ps.close();
                set.close();
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(UUID uuid){
        if(!isConnectionOnline()){
            return false;
        }
        try {
            PreparedStatement ps = this.SQL.getConnection().prepareStatement("INSERT INTO `cosmetics` (`uuid`) VALUES ('"+uuid.toString()+"');");
            ps.executeUpdate();
            ps.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //TODO NOT COMPLETE
    public void saveUserData(PlayerCosmetic cosmeticData){
        if(!isConnectionOnline())
            return;

        Bukkit.getScheduler().runTaskAsynchronously(BWCosmetics.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE `cosmetics` SET `bowtrial` = '"+cosmeticData.getPlayerBowTrail().getName()+"' , `shopkeeper` = '"+cosmeticData.getPlayerShopkeeper().getName()+"';");
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e) {
                    plugin.getLogger().severe("Unable to save player data for "+cosmeticData.getPlayerUUID());
                    plugin.getLogger().severe("Check stacktrace below");
                    e.printStackTrace();
                    return;
                }
            }
        });
    }


}

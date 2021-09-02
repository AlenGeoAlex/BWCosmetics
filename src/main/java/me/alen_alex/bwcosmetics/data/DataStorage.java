package me.alen_alex.bwcosmetics.data;

import me.Abhigya.core.database.sql.SQL;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.Abhigya.core.database.sql.sqlite.SQLite;
import me.alen_alex.bwcosmetics.BWCosmetics;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataStorage {

    private boolean mysqlEnabled;
    private SQLDatabase sqlDatabase;
    private SQL SQL;
    private BWCosmetics plugin;

    public DataStorage(BWCosmetics plugin, boolean mysqlEnabled) {
        this.mysqlEnabled = mysqlEnabled;
        this.plugin = plugin;
    }

    public void build(){
        if(mysqlEnabled){
            this.sqlDatabase = new HikariClientBuilder("jdbc:mysql://"+BWCosmetics.getConfiguration().getSqlHost()+":"+BWCosmetics.getConfiguration().getSqlPort()+"/"+BWCosmetics.getConfiguration().getSqlDatabase(),BWCosmetics.getConfiguration().getSqlUsername(),BWCosmetics.getConfiguration().getSqlPassword(),true).setMaximumPoolSize(10).build();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                        PreparedStatement ps = SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `cosmetics` (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,`uuid` VARCHAR(50) NOT NULL,`death` VARCHAR(30),`killeffect` VARCHAR(30) NOT NULL, `victorydance` VARCHAR(30) NOT NULL,`bowtrial` VARCHAR(30) NOT NULL);");
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}

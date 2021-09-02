package me.alen_alex.bwcosmetics.config;

import de.leonhard.storage.Config;
import me.alen_alex.bwcosmetics.BWCosmetics;

public class Configuration {

    private Config config;
    private String sqlHost,sqlUsername,sqlPassword,sqlPort,sqlDatabase,prefixMain;
    private boolean usingMysql,sslEnabled;


    public boolean createConfiguration(BWCosmetics plugin){
        config = BWCosmetics.getFileUtils().createConfiguration(plugin);
        if(config != null){
            loadConfiguration();
            return true;
        }else
            return false;
    }

    private void loadConfiguration(){
        usingMysql = config.getBoolean("server.use-mysql");
        sqlHost = config.getString("server.host");
        sqlPort = config.getString("server.port");
        sqlUsername = config.getString("server.username");
        sqlPassword = config.getString("server.password");
        sqlDatabase = config.getString("server.database");
        sslEnabled = config.getBoolean("server.usessl");
    }

    public Config getConfig() {
        return config;
    }

    public String getSqlHost() {
        return sqlHost;
    }

    public String getSqlUsername() {
        return sqlUsername;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public String getSqlPort() {
        return sqlPort;
    }

    public String getSqlDatabase() {
        return sqlDatabase;
    }

    public String getPrefixMain() {
        return prefixMain;
    }

    public boolean isUsingMysql() {
        return usingMysql;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }
}

package me.alen_alex.bwcosmetics.utility;

import de.leonhard.storage.Config;
import de.leonhard.storage.Json;
import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.internal.settings.ConfigSettings;
import de.leonhard.storage.internal.settings.DataType;
import de.leonhard.storage.internal.settings.ReloadSettings;
import me.alen_alex.bwcosmetics.BWCosmetics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public void generateParentFolder(BWCosmetics plugin){
        if(plugin.getDataFolder().exists())
            return;
        plugin.getDataFolder().mkdirs();
        plugin.getLogger().info("Successfully created plugin folder");
    }

    public Config createConfiguration(BWCosmetics plugin){
        generateParentFolder(plugin);
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        Config createConfig = LightningBuilder
                .fromFile(configFile)
                .addInputStream(plugin.getResource("config.yml"))
                .setDataType(DataType.SORTED)
                .setConfigSettings(ConfigSettings.PRESERVE_COMMENTS)
                .setReloadSettings(ReloadSettings.MANUALLY)
                .createConfig();
        Config config = new Config("config.yml","plugins"+File.separator+plugin.getDescription().getName());
        config.setDefault("version",plugin.getDescription().getVersion());
        plugin.getLogger().info("Configuration has been generated/found");
        plugin.getLogger().info("Configuration version - "+config.get("version"));
        return config;
    }

    public boolean generateFolder(BWCosmetics plugin,String folderName){
        File file = new File(plugin.getDataFolder()+File.separator+folderName);
        if(file.exists())
            return false;
        else{
            file.mkdirs();
            plugin.getLogger().info("Successfully created folder with path "+file.getPath());
            return true;
        }
    }

    public Yaml createFile(BWCosmetics plugin, InputStream is, String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath(),is);
    }

    public Yaml createFile(BWCosmetics plugin,String fileName){
        return new Yaml(fileName,plugin.getDataFolder().getPath());
    }

    public Yaml createFile(BWCosmetics plugin,InputStream is,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Yaml(fileName,plugin.getDataFolder()+File.separator+folderName,is);
    }

    public Yaml createFile(BWCosmetics plugin,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Yaml(fileName,plugin.getDataFolder()+File.separator+folderName);
    }

    public Json createJSONFile(BWCosmetics plugin,String fileName,String folderName){
        if(!generateFolder(plugin,folderName))
            plugin.getLogger().info("The folder"+folderName+" already exist..Continuing creation of the file "+fileName);
        return new Json(fileName,plugin.getDataFolder()+File.separator+folderName);

    }



}

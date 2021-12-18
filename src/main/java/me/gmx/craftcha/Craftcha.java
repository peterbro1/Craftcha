package me.gmx.craftcha;

import me.gmx.craftcha.command.CmdCraftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.config.Settings;
import me.gmx.craftcha.core.BConfig;
import me.gmx.craftcha.listener.PlayerListener;
import me.gmx.craftcha.objects.CaptchaManager;
import me.gmx.craftcha.objects.SpamManager;
import me.gmx.craftcha.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Craftcha extends JavaPlugin {

    Logger logger;
    private static Craftcha ins;
    public BConfig langConfig, mainConfig;
    private FileConfiguration commandConfig;
    private File commandFile;
    public SpamManager spam;

    @Override
    public void onEnable(){
        ins = this;
        logger = getLogger();

        logger.log(Level.INFO, String.format("[%s] Successfully enabled version %s!", new Object[] { getDescription().getName(), getDescription().getVersion() }));
        registerCommands();
        registerListeners();
        initConfigs();
        reloadConfig();
        this.spam = new SpamManager(getInstance(), ConfigUtil.getCommands());
    }

    @Override
    public void onDisable(){
        CaptchaManager.captchas.clear();
    }

    private void initConfigs(){
        try {
            commandFile = new File(getDataFolder(), "commands.yml");
            if (!commandFile.exists()) {
                commandFile.getParentFile().mkdirs();
                saveResource("commands.yml", true);
            }
            commandConfig= new YamlConfiguration();
            commandConfig.load(commandFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),getInstance());

    }
    private void registerCommands(){
        getCommand("craftcha").setExecutor(new CmdCraftcha(getInstance()));
    }


    public void reloadConfig() {
        this.langConfig = new BConfig(this,"Lang.yml");
        Lang.setConfig(this.langConfig);
        this.mainConfig = new BConfig(this,"Settings.yml");
        Settings.setConfig(this.mainConfig);

            try{
                commandConfig.load(new File(getDataFolder(), "commands.yml"));
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    public FileConfiguration getCommandConfig(){
        return this.commandConfig;
    }

    public void saveLang(){
        this.langConfig.save();
    }
    public void saveMain(){
        this.mainConfig.save();
    }
    public void log(String message){
        logger.log(Level.INFO,"["+getDescription().getName()+"] " + message);
    }

    public static Craftcha getInstance(){return ins;}


}

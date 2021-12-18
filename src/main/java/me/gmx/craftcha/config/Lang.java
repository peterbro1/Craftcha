package me.gmx.craftcha.config;

import me.gmx.craftcha.core.BConfig;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;


public enum Lang {

    PREFIX("&6&lCraftcha> &r "),
    MSG_ERROR("Error occured, please contact server developer."),
    MSG_CRAFTCHA_USAGE("&4Incorrect usage! See /CRAFTCHA help."),
    MSG_USAGE_SUBCOMMAND("Incorrect usage. Correct usage is %usage%"),
    MSG_NOACCESS("You don't have access to this command."),
    MSG_CONFIGRELOADED("Config reloaded."),
    LANG_CONSOLE("The console cannot perform this action."),
    MSG_CRAFTCHA_HELP("&2Insert generic help here."),
    DEATH_KICK("You failed the captcha too many times!"),
    MSG_CRAFTCHA_SUCCESS("&aYou have successfully completed the captcha"),
    MSG_CRAFTCHA_CLOSE("&4Please select the correct item to continue.");


    private String defaultValue;
    private static BConfig config;

    Lang(String str){
        defaultValue = str;
    }


    public String getPath() { return name(); }

    public String getDefaultValue() { return this.defaultValue; }

    public String toString() { return fixColors(config.getConfig().getString(getPath())); }

    public static void setConfig(BConfig paramBConfig) {
        config = paramBConfig;
        load();
    }

    public List<String> getStringList(){
        return Arrays.asList(toString().split("/"));
    }

    public String toMsg() {
        boolean bool = true;
        if (bool) {
            return fixColors(config.getConfig().getString(PREFIX.getPath()) + config.getConfig()
                    .getString(getPath()));
        }
        return fixColors(config.getConfig().getString(getPath()));
    }

    public void set( String o){
        config.getConfig().set(getPath(),o);
    }

    private static void load() {
        for (Lang lang : values()) {
            if (config.getConfig().getString(lang.getPath()) == null) {
                config.getConfig().set(lang.getPath(), lang.getDefaultValue());
            }
        }
        config.save();
    }


    private String fixColors(String paramString) {
        if (paramString == null)
            return "";
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }
}

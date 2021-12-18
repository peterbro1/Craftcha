package me.gmx.craftcha.util;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.objects.CooldownCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class ConfigUtil {

    public void create(String file) {
        final JavaPlugin plugin = Craftcha.getInstance();
        try {
            final File dataFolder = plugin.getDataFolder();

            file = file.replace("%datafolder%", dataFolder.toPath().toString());

            final File configFile = new File(file);

            if (!configFile.exists()) {
                final String[] files = file.split("/");
                final InputStream inputStream = plugin.getClass().getClassLoader()
                        .getResourceAsStream(files[files.length - 1]);
                final File parentFile = configFile.getParentFile();

                if (parentFile != null)
                    parentFile.mkdirs();

                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath());
                    plugin.getLogger().log(Level.INFO, ("[%pluginname%] File " + configFile + " has been created!")
                            .replace("%pluginname%", plugin.getDescription().getName()));
                } else
                    configFile.createNewFile();
            }
        } catch (final IOException e) {
            plugin.getLogger().log(Level.INFO, ("[%pluginname%] Unable to create configuration file!")
                    .replace("%pluginname%", plugin.getDescription().getName()));
        }
    }


    public static ArrayList<CooldownCommand> getCommands() {
        ArrayList<CooldownCommand> cmds = new ArrayList<>();
        for (String sec : Craftcha.getInstance().getCommandConfig().getKeys(false)) {
            ArrayList<String> aliases = new ArrayList<>();
            int a, b = 0;
            Craftcha.getInstance().getCommandConfig().getStringList(sec + ".aliases").forEach(s -> aliases.add(s));
            a = Craftcha.getInstance().getCommandConfig().getInt(sec + ".timeframe");
            b = Craftcha.getInstance().getCommandConfig().getInt(sec + ".max");
            cmds.add(new CooldownCommand(b,a,aliases));
        }
        return cmds;
    }




}

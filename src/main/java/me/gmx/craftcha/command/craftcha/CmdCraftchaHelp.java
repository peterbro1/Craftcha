package me.gmx.craftcha.command.craftcha;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.core.BSubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CmdCraftchaHelp extends BSubCommand {

    public CmdCraftchaHelp(){
        this.aliases.add("help");
        this.correctUsage = "/craftcha help";
        this.permission = "craftcha.help";
    }

    @Override
    public void execute() {
        msg(Lang.PREFIX.toString() + "&6&l----Craftcha Commands----");
        msg(Lang.PREFIX.toString() + "&6/craftcha help " + "&9- Display this help page");
        msg(Lang.PREFIX.toString() + "&6/craftcha reload " + "&9- Reload configs");
        msg(Lang.PREFIX.toString() + "&6/craftcha captcha [player]" + "&9- Give a player a captcha");


        msg(Lang.PREFIX.toString() + "&4&lVersion: " + ChatColor.AQUA + Craftcha.getInstance().getDescription().getVersion());


    }
}

package me.gmx.craftcha.command.craftcha;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.core.BSubCommand;
import me.gmx.craftcha.objects.CaptchaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdCraftchaCaptcha extends BSubCommand {

    public CmdCraftchaCaptcha(){
        this.aliases.add("captcha");
        this.aliases.add("give");
        this.correctUsage = "/craftcha captcha [player]";
        this.permission = "craftcha.captcha";
    }

    @Override
    public void execute() {
        if (args.length != 1){
            sendCorrectUsage();
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);

        if (p == null){
            msg("&4Cannot find player " + args[1]);
            return;
        }

        if (CaptchaManager.captchas.containsKey(p)) {
            CaptchaManager.tryOpen(p);
        }else {
            CaptchaManager.generateCaptcha(27,p).open();
        }
        msg("Player captcha'd successfully!");
        successPing();
    }
}

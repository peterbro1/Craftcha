package me.gmx.craftcha.command;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.command.craftcha.CmdCraftchaCaptcha;
import me.gmx.craftcha.command.craftcha.CmdCraftchaHelp;
import me.gmx.craftcha.command.craftcha.CmdCraftchaReload;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.core.BCommand;
import me.gmx.craftcha.core.BSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdCraftcha extends BCommand implements CommandExecutor {

    public CmdCraftcha(Craftcha instance) {
        super(instance);
        this.subcommands.add(new CmdCraftchaHelp());
        this.subcommands.add(new CmdCraftchaReload());
        this.subcommands.add(new CmdCraftchaCaptcha());
    }

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        if (arg3.length != 0)
            for (BSubCommand cmd : this.subcommands) {
                if (cmd.aliases.contains(arg3[0])) {
                    cmd.execute(arg0, arg3);
                    return true;
                }
            }
        else{

        }
        arg0.sendMessage(Lang.MSG_CRAFTCHA_USAGE.toMsg());

        return true;
    }

}

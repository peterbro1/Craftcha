package me.gmx.craftcha.command.craftcha;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.core.BSubCommand;

public class CmdCraftchaReload extends BSubCommand {


    public CmdCraftchaReload(){
        this.aliases.add("reload");
        this.permission = "craftcha.reload";
        this.correctUsage = "/craftcha reload";
        this.senderMustBePlayer = false;
    }
    @Override
    public void execute() {
        Craftcha.getInstance().reloadConfig();
        msg(Lang.MSG_CONFIGRELOADED.toMsg());
    }

}

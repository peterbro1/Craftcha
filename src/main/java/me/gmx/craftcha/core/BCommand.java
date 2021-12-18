package me.gmx.craftcha.core;


import me.gmx.craftcha.Craftcha;

import java.util.ArrayList;

public class BCommand {

    public Craftcha main;
    public ArrayList<BSubCommand> subcommands;

    public BCommand(Craftcha ins) {
        this.main = ins;
        subcommands = new ArrayList<BSubCommand>();
    }

}

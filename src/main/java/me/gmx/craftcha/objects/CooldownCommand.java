package me.gmx.craftcha.objects;

import java.util.ArrayList;
import java.util.Collection;


//LOL this should be illegal
public class CooldownCommand {
    public int MAX_IN_TIMEFRAME;
    public int TIMEFRAME;
    public ArrayList<String> aliases;


    public CooldownCommand(int max, int time, ArrayList<String> aliases){
        this.MAX_IN_TIMEFRAME = max;
        this.TIMEFRAME = time;
        this.aliases = aliases;
    }

    public boolean matchString(String s){
        for (String cmd : aliases)
            if (s.contains(cmd))
                return true;
            return false;
    }

}

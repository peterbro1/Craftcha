package me.gmx.craftcha.objects;

import org.bukkit.Bukkit;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class CooldownUser {

    public UUID uuid;
    public int count;
    public int max;
    public CooldownCommand cmd;
    public Timestamp end;

    public CooldownUser(UUID player, CooldownCommand cmd){
        this.end = new Timestamp(System.currentTimeMillis() + (cmd.TIMEFRAME * 1000));
        this.uuid = player;
        this.max = cmd.MAX_IN_TIMEFRAME;
        this.count = 0;
        this.cmd = cmd;
    }

    public boolean isDead(){
        return end.before(Timestamp.from(Instant.now()));
    }

    public void increment(){
        this.count++;
    }

    public boolean check(){
        return this.count <= this.max;
    }


}

package me.gmx.craftcha.objects;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SpamManager {
    private Craftcha ins;
    private ArrayList<CooldownCommand> cmds;
    private int[] tasks;
    private ArrayList<CooldownUser> map;
    public SpamManager(Craftcha ins, ArrayList<CooldownCommand> cmds){
        this.ins = ins;
        map = new ArrayList<>();
        tasks = new int[cmds.size()];
        this.cmds = cmds;
        a();
    }

    public void a(){
        new BukkitRunnable(){
            public void run(){
                byte b = 0;
                System.out.println(Lang.PREFIX.toString() + "Cleaning up dead users... ");
                for (CooldownUser user : map){
                    if (user.isDead()) {
                        map.remove(user);
                        b++;
                    }
                }
                System.out.println(Lang.PREFIX.toString() + "Cleaned up " + b + " users.");
            }
        }.runTaskTimerAsynchronously(ins,1L, Settings.CLEANUP_INTERVAL.getNumber() * 20);

        new BukkitRunnable(){
            public void run(){
                for (Player p : CaptchaManager.captchas.keySet()){
                    p.closeInventory();
                }
                CaptchaManager.captchas.clear();
            }
        }.runTaskTimer(ins,0L,Settings.RESET_INTERVAL.getNumber()*20);

    }

    public void incrementCommand(Player p, String cmd){

        CooldownCommand c = fromString(cmd);
        for (CooldownUser user : map)
            if (user.uuid.equals(p.getUniqueId()) && user.cmd == c){
                user.increment();
                return;
            }
        map.add(new CooldownUser(p.getUniqueId(), c));

    }


    public boolean isCooldownCommand(String s){


        /////
        for (CooldownUser u : map) {
            //Bukkit.broadcastMessage(Bukkit.getPlayer(u.uuid).getName() + " - " + u.cmd.aliases.get(0) + " - " + u.count);
        }
        ////

        for (CooldownCommand c : cmds)
            if (c.matchString(s))
                return true;

        return false;
    }

    public boolean canExecute(Player p, String s){

        for (CooldownUser u : map){
            if (u.uuid.equals(p.getUniqueId()) && !u.isDead()){
                return u.check();
            }
        }
        map.removeIf(cooldownUser -> {
            return cooldownUser.uuid.equals(p.getUniqueId()) ? (cooldownUser.isDead() || !cooldownUser.check()) : false;
        });
        return true;
    }

    public CooldownCommand fromString(String s){
        for (CooldownCommand c : cmds){
            if (c.matchString(s))
                return c;
        }
        return null;
    }

    public boolean handleCommand(Player p, String s){
        if (canExecute(p,s)){
            incrementCommand(p,s);
            return true;
        }
        CaptchaManager.give(p);
        return false;

    }

    public void removeAll(Player p){
        map.removeIf(cooldownUser -> {return cooldownUser.uuid.equals(p.getUniqueId()); });
    }



}

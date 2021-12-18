package me.gmx.craftcha.objects;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaptchaManager {

    public static final byte[] fillerData = new byte[]{
            0x0,
            0x1,
            0x2,
            0x3,
            0x4,
            0x5,
            0x6,
            0x7,
            0x8,
            0x9,
            0xa,
            0xb,
            0xc,
            0xd,
            0xe,
            0xf,
    };

    public static final ItemStack TARGET_ITEMSTACK = new ItemStack(Material.SKULL_ITEM,1,(short)3);

    //No more than 5 players will have a captcha at a time. Storing player object is fine
    public static Map<Player, Captcha> captchas;
    private Craftcha ins;

    static {
        captchas = new HashMap<>();
    }

   public static Captcha generateCaptcha(int size, Player p){
        Captcha c = new Captcha(size, p, fillerData, Material.STAINED_GLASS_PANE,new ItemStack(Material.valueOf(Settings.CAPTCHA_MATERIAL.getString()), 1));
        if (!captchas.containsKey(p))
        captchas.put(p,c);
        return c;
   }

   public static boolean tryOpen(Player p){
        if (captchas.containsKey(p)) {
            captchas.get(p).open();
            return true;
        }else return false;
   }


   public static void give(Player p){
        if (!tryOpen(p)){
            generateCaptcha(27,p);
            tryOpen(p);
        }
   }

    public static Captcha fromPlayer(Player p){
        return captchas.get(p);
    }

    public static <T extends HumanEntity> void removeFromCaptcha(T a){
        if (captchas.containsKey(a))
            captchas.remove(a);
    }

}

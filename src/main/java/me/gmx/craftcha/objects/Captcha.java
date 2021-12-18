package me.gmx.craftcha.objects;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static me.gmx.craftcha.objects.CaptchaManager.fillerData;
import static me.gmx.craftcha.objects.CaptchaManager.generateCaptcha;

public class Captcha {



    private Random random;
    private Inventory inventory;
    private ItemStack filler, target;
    private Player p;
    public int size, target_id;
    private UUID id;



    public Captcha(int size, Player p, byte[] colors, Material filler, ItemStack target){
        this.size = size;
        this.random = new Random();
        this.p = p;
        this.id = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, size, Settings.CAPTCHA_INVENTORY_NAME.getString());
        /*SkullMeta m = (SkullMeta) target.getItemMeta();
        m.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("e6a61b70-a777-43a3-bb72-e34397a1b6d9")));
        m.setDisplayName(Settings.CAPTCHA_CLICKME.getString());
        m.setLore(Arrays.asList(this.id.toString()));
        target.setItemMeta(m);*/
        ItemMeta m = target.getItemMeta();
        m.setDisplayName(Settings.CAPTCHA_CLICKME.getString());
        m.setLore(Arrays.asList(this.id.toString()));
        target.setItemMeta(m);
        this.target = target;

        this.filler = new ItemStack(filler,1);
        ItemMeta fillerMeta = this.filler.getItemMeta();
        fillerMeta.setDisplayName(Settings.CAPTCHA_FILLER.getString());
        this.filler.setItemMeta(fillerMeta);
    }

    private void generate(){
        for (int i = 0; i < inventory.getSize(); i++) {
            filler.setDurability((short)(fillerData[random.nextInt(fillerData.length)]));
            inventory.setItem(i, this.filler);
        }
        this.target_id = random.nextInt(inventory.getSize());
        inventory.setItem(this.target_id,target);
    }



    public void open(){
        if (!p.isOnline())
            return;
        generate();

        p.openInventory(inventory);

    }

    public UUID getId(){
        return this.id;
    }






}

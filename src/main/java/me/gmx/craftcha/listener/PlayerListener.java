package me.gmx.craftcha.listener;

import me.gmx.craftcha.Craftcha;
import me.gmx.craftcha.config.Lang;
import me.gmx.craftcha.config.Settings;
import me.gmx.craftcha.objects.Captcha;
import me.gmx.craftcha.objects.CaptchaManager;
import me.gmx.craftcha.objects.CooldownCommand;
import me.gmx.craftcha.objects.SpamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerListener implements Listener {

    @EventHandler
    public void closeInventoryEvent(InventoryCloseEvent e){
        if (!e.getInventory().getName().equals(Settings.CAPTCHA_INVENTORY_NAME.getString()))
            return;
        Captcha c = CaptchaManager.fromPlayer((Player)e.getPlayer());
        if (c != null) {
            new BukkitRunnable(){
                public void run(){
                    c.open();
                }
            }.runTaskLater(Craftcha.getInstance(),5);
            e.getPlayer().damage(Settings.CAPTCHA_CLOSE_DAMAGE.getNumber());
            ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_HIT,1,1);
            e.getPlayer().sendMessage(Lang.MSG_CRAFTCHA_CLOSE.toMsg());
        }else
            e.getPlayer().sendMessage(Lang.MSG_CRAFTCHA_SUCCESS.toMsg());
    }


    @EventHandler
    public void inventoryInteractEvent(InventoryClickEvent e){
        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName().equals(Settings.CAPTCHA_INVENTORY_NAME.getString())) {
            e.setCancelled(true);
            Captcha cap = CaptchaManager.fromPlayer((Player) e.getWhoClicked());
            if (cap == null)
                return;
                if (e.getSlot() == cap.target_id) {
                    CaptchaManager.removeFromCaptcha(e.getWhoClicked());
                    Craftcha.getInstance().spam.removeAll((Player) e.getWhoClicked());
                    ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                }
                new BukkitRunnable() {
                    public void run() {
                        e.getWhoClicked().closeInventory();
                    }
                }.runTaskLater(Craftcha.getInstance(), 1);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        Player p = e.getPlayer();

        if (p != null){
            if (CaptchaManager.captchas.containsKey(p)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerCommandEvent(PlayerCommandPreprocessEvent e){
        String cmd = e.getMessage();
        if (!Craftcha.getInstance().spam.isCooldownCommand(cmd))
            return;

        Player p = e.getPlayer();

        if (!Craftcha.getInstance().spam.handleCommand(p,cmd) && CaptchaManager.fromPlayer(p) == null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerOpen(InventoryOpenEvent e){

    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent e){
        if (CaptchaManager.fromPlayer(e.getEntity()) != null){
            CaptchaManager.removeFromCaptcha(e.getEntity());
            e.getEntity().kickPlayer(Lang.DEATH_KICK.toString());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerLeave(PlayerJoinEvent e){
        new BukkitRunnable(){
            public void run(){
                if (CaptchaManager.fromPlayer(e.getPlayer()) != null){
                    CaptchaManager.fromPlayer(e.getPlayer()).open();
                }
            }
        }.runTaskLater(Craftcha.getInstance(),5L);

    }

}

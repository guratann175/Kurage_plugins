package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ItemUseSystem implements Listener {
    private final Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);

    /*アイテムを持って右クリックしたときのイベント*/
    /*
    @EventHandler
    public void grenadelLaunch(PlayerInteractEvent event) {
        ItemStack Item = event.getItem();
        Action action = event.getAction();
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        if (Item == null) {
            return;
        }

        //もし右クリックされたら、
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            // もし持っているアイテムが○○○○なら
            if (Item.getType().equals(Material.DIAMOND)) {
                Item.setAmount(Item.getAmount() - 1);
                ItemStack bomb = new ItemStack(Item.getType(), 1);
                Entity drop = loc.getWorld().dropItemNaturally(loc, bomb);
                drop.setVelocity(loc.getDirection().multiply(1));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // 位置.getWorld().createExplosion: 爆発を生成
                        loc.getWorld().createExplosion(drop.getLocation(), 5, false);
                    }
                }.runTaskLater(plugin, 40);
            }
        }
    }*/

    @EventHandler
    // PlayerInteractEvent アイテムをクリックしたときのイベント
    public void GatyaUse(PlayerInteractEvent event) {
        Random rnd = new Random();
        ItemStack Item = event.getItem();
        Action action = event.getAction();
        Player player = event.getPlayer();

        if (Item == null) {
            return;
        }
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            // もし持っているアイテムが
            if (Item.getType().equals(Material.PAPER)) {
                // もしアイテムが1個以上あるならば
                if (Item.getAmount() > 1) {
                    // インベントリからアイテムを消す。
                    Item.setAmount(Item.getAmount() - 1);

                    //確率の設定 (ランダムな値)
                    if (rnd.nextInt(50) < 2) {
                        ItemStack present = new ItemStack(Material.GOLDEN_APPLE, 1);
                        player.getInventory().addItem(present);
                        player.sendMessage("おめでとう！! " + ChatColor.GOLD + "大当たり" + ChatColor.WHITE + "だよ");
                    }
                    else {
                        player.sendMessage("残念… " + ChatColor.AQUA + "はずれ" + ChatColor.WHITE + "だよ");
                    }
                } else {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                }
            }
        }
    }
}

package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class GatyaEvent implements Listener {

    @EventHandler
    public void onPunch(PlayerInteractEvent event){
    }

    @EventHandler
    /*ブロックを壊したときのイベント BlockBreakEvent*/
    public void onBreakBlock(BlockBreakEvent event) {
        Random rnd = new Random();
        Block block = event.getBlock();
        Player player = event.getPlayer();

        /*
        player.sendMessage(ChatColor.GOLD + player.getName() +
                ChatColor.WHITE + "は"
                + ChatColor.GREEN + block.getType().toString().toUpperCase() +
                ChatColor.WHITE + "を壊しました" );
         */

        if (block.getType().equals(Material.STONE)) {
            if (rnd.nextInt(100) < 1) {
                CustomItem.giveItem(player);
            }
        }
    }
}

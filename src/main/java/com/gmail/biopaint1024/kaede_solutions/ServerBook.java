package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class ServerBook implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String nl = "\n";

        // 本を作る
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.setAuthor("Hatano Kaede");
        bookmeta.setTitle(ChatColor.DARK_PURPLE + "このサーバーの仕様");

        ArrayList<String> pages = new ArrayList<String>();

        pages.add(ChatColor.DARK_GREEN + "1:ダイアモンドを投げると、爆発する" + nl +
                "2:explodeコマンドで座標を確認する");
        bookmeta.setPages(pages);
        book.setItemMeta(bookmeta);

        // プレイヤーにアイテムを与える
        // player.getInventory().addItem(book);
    }

}

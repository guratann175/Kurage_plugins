package com.gmail.biopaint1024.kaede_solutions;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CustomItem implements Listener {


    public static void giveItem(Player player) {
        ItemStack Item = new ItemStack(Material.PAPER,1);
        ItemMeta meta = Item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "ガチャ券");

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.WHITE + "何が出るかな?");
        meta.setLore(lore);
        //lore アイテム説明分
        Item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        Item.setItemMeta(meta);
        //itemmetaの更新(変更の適用)


        player.getInventory().addItem(Item);

    }

}
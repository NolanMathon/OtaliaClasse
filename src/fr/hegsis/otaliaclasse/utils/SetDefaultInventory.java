package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetDefaultInventory {

    // Menu qui permet de choisir entre la classe Pirate et Titan lors de la première connection
    public static Inventory setDefaultChooseInventory(Main main) {
        Inventory inv = Bukkit.createInventory(null, 27, main.getConfig().getString("choosing-menu.title").replaceAll("&", "§"));
        ItemStack it;
        ItemMeta im;

        // Bleu foncé
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        for (int i=0; i<27; i++) {
            if (i<10 || i>16) {
                inv.setItem(i, it);
            }
        }

        // Blanc
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        inv.setItem(10, it);
        inv.setItem(16, it);

        // Bleu Clair
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        inv.setItem(11, it);
        inv.setItem(13, it);
        inv.setItem(15, it);

        // Pirate
        it = new ItemStack(Material.getMaterial(main.getConfig().getString("choosing-menu.pirate.item")), 1, (short) main.getConfig().getInt("choosing-menu.pirate.data"));
        im = it.getItemMeta();
        im.setDisplayName(main.getConfig().getString("choosing-menu.pirate.title").replaceAll("&", "§"));
        im.setLore(StringUtils.convertLoreColorCode(main.getConfig().getStringList("choosing-menu.pirate.description")));
        it.setItemMeta(im);
        inv.setItem(12, it);

        // Titan
        it = new ItemStack(Material.getMaterial(main.getConfig().getString("choosing-menu.titan.item")), 1, (short) main.getConfig().getInt("choosing-menu.titan.data"));
        im = it.getItemMeta();
        im.setDisplayName(main.getConfig().getString("choosing-menu.titan.title").replaceAll("&", "§"));
        im.setLore(StringUtils.convertLoreColorCode(main.getConfig().getStringList("choosing-menu.titan.description")));
        it.setItemMeta(im);
        inv.setItem(14, it);

        return inv;
    }
}

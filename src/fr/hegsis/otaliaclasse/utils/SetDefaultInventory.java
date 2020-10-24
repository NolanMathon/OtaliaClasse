package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
        it = new ItemStack(Material.getMaterial(main.getConfig().getString("choosing-menu.dieu.item")), 1, (short) main.getConfig().getInt("choosing-menu.dieu.data"));
        im = it.getItemMeta();
        im.setDisplayName(main.getConfig().getString("choosing-menu.dieu.title").replaceAll("&", "§"));
        im.setLore(StringUtils.convertLoreColorCode(main.getConfig().getStringList("choosing-menu.dieu.description")));
        it.setItemMeta(im);
        inv.setItem(12, it);

        // Titan
        it = new ItemStack(Material.getMaterial(main.getConfig().getString("choosing-menu.demon.item")), 1, (short) main.getConfig().getInt("choosing-menu.demon.data"));
        im = it.getItemMeta();
        im.setDisplayName(main.getConfig().getString("choosing-menu.demon.title").replaceAll("&", "§"));
        im.setLore(StringUtils.convertLoreColorCode(main.getConfig().getStringList("choosing-menu.demon.description")));
        it.setItemMeta(im);
        inv.setItem(14, it);

        return inv;
    }

    public static Inventory setDefaultRewardMenu(Main main) {
        Inventory inv = Bukkit.createInventory(null, 27, main.getConfig().getString("rewards-menu.title").replaceAll("&", "§"));
        ItemStack it;
        ItemMeta im;

        // Bleu foncé
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        int[] emplacement = new int[] {10, 11, 12, 14, 15, 16};

        for (int i=0; i<27; i++) {
            if (i<10 || i>16) {
                inv.setItem(i, it);
            }
        }

        for (int i=1; i<7; i++) {
            it = new ItemStack(Material.getMaterial(main.getConfig().getString("rewards-menu.level" + i + ".item")), 1, (short) main.getConfig().getInt("rewards-menu.level" + i + ".data"));
            im = it.getItemMeta();
            im.setDisplayName(main.getConfig().getString("rewards-menu.locked").replaceAll("&", "§").replaceAll("%level%", ""+i));
            it.setItemMeta(im);
            inv.setItem(emplacement[i-1], it);
        }

        // Blanc
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        inv.setItem(13, it);

        // Flèche de retour
        it = new ItemStack(Material.ARROW);
        im = it.getItemMeta();
        im.setDisplayName("§c§lRETOUR");
        it.setItemMeta(im);
        inv.setItem(22, it);

        return inv;
    }

    public static Inventory setDefaultClasseMenuInventory(Main main, ClasseType classeType) {
        Inventory inv = Bukkit.createInventory(null, 27, main.getConfig().getString("quest-type-menu.title").replaceAll("&", "§"));
        ItemStack it;
        ItemMeta im;
        List<QuestType> questTypes = new ArrayList<>();
        int[] emplacement;

        for (Quest q : main.classes.get(classeType).getQuestList()) {
            if (!questTypes.contains(q.getQuestType())) {
                questTypes.add(q.getQuestType());
            }
        }
        emplacement = getEmplacement(questTypes.size());

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

        for (int i=0; i<questTypes.size(); i++) {
            it = new ItemStack(Material.getMaterial(main.getConfig().getString("quest-type-menu.categories." + questTypes.get(i) + ".item")), 1, (short) main.getConfig().getInt("quest-type-menu.categories." + questTypes.get(i) + ".data"));
            im = it.getItemMeta();
            im.setDisplayName(main.getConfig().getString("quest-type-menu.categories." + questTypes.get(i) + ".title").replaceAll("&", "§"));
            im.setLore(StringUtils.convertLoreColorCode(main.getConfig().getStringList("quest-type-menu.categories." + questTypes.get(i) + ".description")));
            it.setItemMeta(im);
            inv.setItem(emplacement[i], it);
        }

        // Blanc
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);

        for (int i=0; i<inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, it);
            }
        }

        it = new ItemStack(Material.getMaterial(main.getConfig().getString("quest-type-menu.go-to-reward-menu.item")), 1, (short) main.getConfig().getInt("quest-type-menu.go-to-reward-menu.data"));
        im = it.getItemMeta();
        im.setDisplayName(main.getConfig().getString("quest-type-menu.go-to-reward-menu.name").replaceAll("&", "§"));
        it.setItemMeta(im);

        inv.setItem(22, it);

        return inv;
    }

    private static int[] getEmplacement(int size) {
        int[] emplacement = null;
        switch (size) {
            case 1:
                emplacement = new int[]{13};
                break;
            case 2:
                emplacement = new int[]{12,14};
                break;
            case 3:
                emplacement = new int[]{12,13,14};
                break;
            case 4:
                emplacement = new int[]{11,12,14,15};
                break;
            case 5:
                emplacement = new int[]{11,12,13,14,15};
                break;
        }
        return emplacement;
    }
}

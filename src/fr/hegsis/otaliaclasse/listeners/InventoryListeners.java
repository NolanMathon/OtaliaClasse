package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.ClassManager;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.QuestType;
import fr.hegsis.otaliaclasse.utils.OpenInventories;
import fr.hegsis.otaliaclasse.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    private Main main;
    public InventoryListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        // Si le nom de l'inventaire cliqué est le même que celui du choix entre pirta et titan
        if (e.getInventory().getTitle().equalsIgnoreCase(main.firstChoose.getTitle())) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();

            if (!item.hasItemMeta()) return;

            if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName()) && !item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(14).getItemMeta().getDisplayName())) return;

            ClasseType classeType = null;
            int[] activeQuests = null;
            int[] progressQuests = null;

            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName())) {
                classeType = ClasseType.PIRATE;
                activeQuests = new int[]{1,11,21,31,41};
                progressQuests = new int[] {0,0,0,0,0};
            }

            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(14).getItemMeta().getDisplayName())) {
                classeType = ClasseType.TITAN;
                activeQuests = new int[]{1,11,21,31};
                progressQuests = new int[] {0,0,0,0};
            }

            for(HumanEntity he:e.getViewers()) {
                if(he instanceof Player) {
                    Player p = (Player) he;
                    Profile profile = new Profile(p.getUniqueId(), p.getName(), classeType, 0, 0, null, activeQuests, progressQuests, null);
                    main.saveProfileOnJson(p, profile);
                    main.playersProfile.put(p.getName(), profile);
                    p.closeInventory();
                    Utils.sendTitle(p, main.getConfig().getString("title.join-classe.title").replaceAll("&", "§"), main.getConfig().getString("title.join-classe.subtitle").replaceAll("&", "§").replaceAll("%classe%", classeType.toString()));
                    return;
                }
            }

        }

        // Si le nom de l'inventaire est celui du menus des catégories de quêtes
        if (e.getInventory().getTitle().equalsIgnoreCase(main.getConfig().getString("quest-type-menu.title").replaceAll("&", "§"))) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();

            if (!item.hasItemMeta()) return;

            if (item.getType() == Material.STAINED_GLASS_PANE) return;

            Player p = (Player) e.getWhoClicked();

            if (item.getType() == e.getInventory().getItem(22).getType() && item.getItemMeta().getDisplayName().equalsIgnoreCase(e.getInventory().getItem(22).getItemMeta().getDisplayName())) {
                OpenInventories.openRewardsMenu(p, main);
                return;
            }

            for (QuestType q : QuestType.values()) {
                if (item.getType() == Material.getMaterial(main.getConfig().getString("quest-type-menu.categories."+q+".item"))
                        && item.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("quest-type-menu.categories."+q+".title").replaceAll("&", "§"))) {
                    OpenInventories.openQuestMenu(p, q, item, main);
                    return;
                }
            }

        }

        // Si le nom de l'inventaire est celui du menus des quêtes
        if (e.getInventory().getTitle().equalsIgnoreCase(main.getConfig().getString("quests-menu.title").replaceAll("&", "§"))) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();

            if (!item.hasItemMeta()) return;

            if (item.getType() == Material.ARROW && item.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRETOUR")) {
                Player p = (Player) e.getWhoClicked();
                OpenInventories.openCategoriesMenu(p, main);
                return;
            }
        }

        // Si le nom de l'inventaire est celui du menus des récompenses
        if (e.getInventory().getTitle().equalsIgnoreCase(main.rewardMenu.getTitle())) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();

            if (!item.hasItemMeta()) return;

            if (item.getType() == Material.STAINED_GLASS_PANE || item.getType() == Material.SKULL_ITEM) return;

            if (item.getType() == Material.ARROW && item.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRETOUR")) {
                Player p = (Player) e.getWhoClicked();
                OpenInventories.openCategoriesMenu(p, main);
                return;
            }

            int[] emplacement = new int[] {10, 11, 12, 14, 15, 16};
            for (int i=0; i<6; i++) {
                if (item.getType() == main.rewardMenu.getItem(emplacement[i]).getType()) {
                    ClassManager.giveReward((Player) e.getWhoClicked(), i+1, main);
                    return;
                }
            }
        }
    }

}

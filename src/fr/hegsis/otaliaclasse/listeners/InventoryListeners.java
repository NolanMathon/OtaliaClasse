package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestType;
import fr.hegsis.otaliaclasse.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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
                    main.classes.get(classeType).addPlayer(p.getName());
                    Profile profile = new Profile(p.getUniqueId(), p.getName(), classeType, 0, 0, null, activeQuests, progressQuests);
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

            for (QuestType q : QuestType.values()) {
                if (item.getType() == Material.getMaterial(main.getConfig().getString("quest-type-menu.categories."+q+".item"))
                        && item.getItemMeta().getDisplayName().equalsIgnoreCase(main.getConfig().getString("quest-type-menu.categories."+q+".title").replaceAll("&", "§"))) {
                    openQuestMenu(p, q, item);
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
                ClasseCommand.openCategoriesMenu(p, main);
                return;
            }
        }
    }

    private void openQuestMenu(Player p, QuestType questType, ItemStack item) {
        Profile profile = main.playersProfile.get(p.getName());
        int[] questDone = profile.getDoneQuestId();
        int[] questActive = profile.getActiveQuestId();
        List<Quest> quests = main.classes.get(profile.getClasseType()).getQuestList();
        List<Quest> questsDone = new ArrayList<>();
        Quest activeQuest = null;
        int progression = 0;

        for (int i=0; i<questDone.length; i++) {
            if (quests.get(questDone[i]-1).getQuestType() == questType) {
                questsDone.add(quests.get(questDone[i]-1));
            }
        }

        for (int i=0; i<questActive.length; i++) {
            if (quests.get(questActive[i]-1).getQuestType() == questType) {
                activeQuest = quests.get(questActive[i]-1);
                progression = profile.getProgressionQuest()[i];
                break;
            }
        }

        /*for (Quest q : questsDone) {
            System.out.println(q.getId() + " - " + q.getQuestAction() + " - " + q.getAmount() + " - " + q.getEntityType() + " - " + q.getExpRewards());
        }

        System.out.println(activeQuest.getId() + " - " + activeQuest.getQuestAction() + " - " + activeQuest.getAmount() + " - " + activeQuest.getEntityType() + " - " + activeQuest.getExpRewards() + " - " + progression);*/

        int nbQuests = 0;
        for (Quest q : quests) {
            if (q.getQuestType() == questType) {
                nbQuests++;
            }
        }

        int nbSlot = (nbQuests/5)*9+18;
        if (nbSlot > 54) nbSlot = 54;

        Inventory inv = Bukkit.createInventory(null, nbSlot, main.getConfig().getString("quests-menu.title").replaceAll("&", "§"));

        int[] emplacement = null;

        switch (inv.getSize()) {
            case 27:
                emplacement = new int[] {11, 12, 13, 14, 15};
                break;
            case 36:
                emplacement = new int[] {11, 12, 13, 14, 15, 20, 21, 22, 23, 24};
                break;
            case 45:
                emplacement = new int[] {11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33};
                break;
            case 54:
                emplacement = new int[] {11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42};
                break;
        }

        ItemStack it;
        ItemMeta im;

        // Bleu foncé
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);
        for (int i=0; i<inv.getSize(); i++) {
            inv.setItem(i, it);
        }

        // Blanc
        it = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        im = it.getItemMeta();
        im.setDisplayName(" ");
        it.setItemMeta(im);

        inv.setItem(10, it);
        inv.setItem(16, it);
        if (inv.getSize()>=36) {
            inv.setItem(19, it);
            inv.setItem(25, it);
        }
        if (inv.getSize()>=45) {
            inv.setItem(28, it);
            inv.setItem(34, it);
        }
        if (inv.getSize()>=54) {
            inv.setItem(37, it);
            inv.setItem(43, it);
        }

        ItemStack doneQuestIt = new ItemStack(Material.getMaterial(main.getConfig().getString("quests-menu.quest-done.item")), 1, (short) main.getConfig().getInt("quests-menu.quest-done.data"));;
        ItemStack activeQuestIt = new ItemStack(Material.getMaterial(main.getConfig().getString("quests-menu.ongoing-quest.item")), 1, (short) main.getConfig().getInt("quests-menu.ongoing-quest.data"));
        ItemStack futurQuestIt = new ItemStack(Material.getMaterial(main.getConfig().getString("quests-menu.futur-quest.item")), 1, (short) main.getConfig().getInt("quests-menu.futur-quest.data"));
        im = futurQuestIt.getItemMeta();
        im.setDisplayName(main.getConfig().getString("quests-menu.futur-quest.title").replaceAll("&", "§"));
        futurQuestIt.setItemMeta(im);
        String objectif;

        for (int i=0; i<nbQuests; i++) {

            if (i<questsDone.size()) {
                Quest q = questsDone.get(i);
                if (q.getQuestAction() == QuestAction.POSER) {
                    objectif = "Blocks";
                } else {
                    if (q.getEntityType() == null) {
                        objectif = q.getItem().toString();
                    } else {
                        objectif = q.getEntityType().toString();
                    }
                }
                im.setDisplayName(main.getConfig().getString("quests-menu.quest-done.title")
                        .replaceAll("&", "§")
                        .replaceAll("%action%", ""+q.getQuestAction())
                        .replaceAll("%amount%", ""+q.getAmount())
                        .replaceAll("%objectif%", objectif));
                doneQuestIt.setItemMeta(im);
                inv.setItem(emplacement[i], doneQuestIt);
            } else if (i==questsDone.size()) {
                if (activeQuest.getQuestAction() == QuestAction.POSER) {
                    objectif = "Blocks";
                } else {
                    if (activeQuest.getEntityType() == null) {
                        objectif = activeQuest.getItem().toString();
                    } else {
                        objectif = activeQuest.getEntityType().toString();
                    }
                }
                im = activeQuestIt.getItemMeta();
                im.setDisplayName(main.getConfig().getString("quests-menu.ongoing-quest.title").replaceAll("&", "§")
                        .replaceAll("%action%", ""+activeQuest.getQuestAction())
                        .replaceAll("%amount%", ""+activeQuest.getAmount())
                        .replaceAll("%objectif%", objectif));
                List<String> lore = main.getConfig().getStringList("quests-menu.ongoing-quest.description");
                for (int j=0; j<lore.size(); j++) {
                    lore.set(j, lore.get(j).replaceAll("&", "§").replaceAll("%progression%", ""+progression).replaceAll("%amount%", ""+activeQuest.getAmount()));
                }
                im.setLore(lore);
                activeQuestIt.setItemMeta(im);
                inv.setItem(emplacement[i], activeQuestIt);
            } else {
                inv.setItem(emplacement[i], futurQuestIt);
            }
        }

        it = new ItemStack(Material.ARROW);
        im = it.getItemMeta();
        im.setDisplayName("§c§lRETOUR");
        it.setItemMeta(im);
        inv.setItem(inv.getSize()-5, it);
        inv.setItem(4, item);
        p.openInventory(inv);
    }

}

package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenInventories {

    public static void openRewardsMenu(Player p, Main main) {
        Profile profile = main.playersProfile.get(p.getName());
        int level = profile.getClassLevel();
        int[] levelClaimed = profile.getRewardsClaimed();

        if (levelClaimed == null) {
            levelClaimed = new int[0];
        }

        Inventory inv = Bukkit.createInventory(null, 27, main.rewardMenu.getTitle());
        inv.setContents(main.rewardMenu.getContents().clone());
        ItemStack it;
        ItemMeta im;

        int[] emplacement = new int[] {10, 11, 12, 14, 15, 16};
        for (int i=0; i<emplacement.length; i++) {
            it = inv.getItem(emplacement[i]);
            im = it.getItemMeta();

            if (i < level) {
                im.setDisplayName(main.getConfig().getString("rewards-menu.unlocked").replaceAll("&", "§").replaceAll("%level%", ""+(i+1)));
                for (int reward : levelClaimed) {
                    if (reward == i+1) {
                        im.setDisplayName(main.getConfig().getString("rewards-menu.already-taken").replaceAll("&", "§").replaceAll("%level%", ""+(i+1)));
                        break;
                    }
                }
            } else {
                im.setDisplayName(main.getConfig().getString("rewards-menu.locked").replaceAll("&", "§").replaceAll("%level%", ""+(i+1)));
            }

            it.setItemMeta(im);
            inv.setItem(emplacement[i], it);
        }

        inv.setItem(4, Utils.getGlobalStatsPlayerHead(p, main));

        p.openInventory(inv);
    }

    public static void openCategoriesMenu(Player p, Main main) {
        Profile profile = main.playersProfile.get(p.getName());
        int[] doneQuests = profile.getDoneQuestId();
        Classe classe = main.classes.get(profile.getClasseType());

        List<QuestType> questTypes = new ArrayList<>();
        Map<QuestType, Integer> playerQuestNumberByType = new HashMap<>();
        Map<QuestType, Integer> numberOfQuestsByType = new HashMap<>();

        // On récupère l'emplacement de chaque item de chaque catégorie, la liste du type des quêtes, et le nombre de quête par catégories
        int[] emplacement;
        for (Quest q : classe.getQuestList()) {
            if (!questTypes.contains(q.getQuestType())) {
                questTypes.add(q.getQuestType());
                numberOfQuestsByType.put(q.getQuestType(), 1);
            } else {
                numberOfQuestsByType.replace(q.getQuestType(), numberOfQuestsByType.get(q.getQuestType())+1);
            }

        }
        emplacement = getEmplacement(questTypes.size());

        // On met le nombre de quête par catégorie à 0
        for (int i=0; i<emplacement.length; i++) {
            playerQuestNumberByType.put(questTypes.get(i), 0);
        }

        if (doneQuests != null) {
            for (int i=0; i<doneQuests.length; i++) {
                for (Quest q : classe.getQuestList()) {
                    if (q.getId() == doneQuests[i]) {
                        playerQuestNumberByType.replace(q.getQuestType(), playerQuestNumberByType.get(q.getQuestType())+1);
                        break;
                    }
                }
            }
        }

        Inventory inv = Bukkit.createInventory(null, 27, classe.getClasseInventory().getTitle());
        inv.setContents(classe.getClasseInventory().getContents().clone());

        ItemStack it;
        ItemMeta im;
        int progression, done, available;
        for (int i=0; i<emplacement.length; i++) {
            QuestType questType = questTypes.get(i);
            it = inv.getItem(emplacement[i]);
            im = it.getItemMeta();

            List<String> lore = im.getLore();

            done = playerQuestNumberByType.get(questType);
            available = numberOfQuestsByType.get(questType) - done;
            progression = (done * 100) / numberOfQuestsByType.get(questType);
            for (int j=0; j<lore.size(); j++) {
                lore.set(j, lore.get(j).replaceAll("%progression%", progression + "%").replaceAll("%done%", ""+done).replaceAll("%available%", ""+available));
            }
            im.setLore(lore);
            it.setItemMeta(im);
            inv.setItem(emplacement[i], it);
        }

        inv.setItem(4, Utils.getGlobalStatsPlayerHead(p, main));

        p.openInventory(inv);
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

    public static void openQuestMenu(@NotNull Player p, QuestType questType, ItemStack item, Main main) {
        Profile profile = main.playersProfile.get(p.getName());
        int[] questDone = profile.getDoneQuestId();
        int[] questActive = profile.getActiveQuestId();
        List<Quest> quests = main.classes.get(profile.getClasseType()).getQuestList();
        List<Quest> questsDone = new ArrayList<>();
        Quest activeQuest = null;
        int progression = 0;

        if (questDone != null) {
            for (int i=0; i<questDone.length; i++) {
                if (quests.get(questDone[i]-1).getQuestType() == questType) {
                    questsDone.add(quests.get(questDone[i]-1));
                }
            }
        }

        for (int i=0; i<questActive.length; i++) {
            if (quests.get(questActive[i]-1).getQuestType() == questType) {
                activeQuest = quests.get(questActive[i]-1);
                progression = profile.getProgressionQuest()[i];
                break;
            }
        }

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
                    objectif = "BLOCKS";
                } else {
                    if (q.getEntityType() == null) {
                        if (q.getData() != 0) {
                            ItemStack obj = new ItemStack(q.getItem(), 1, q.getData());
                            objectif = CraftItemStack.asNMSCopy(obj).getName().toUpperCase();
                        } else {
                            objectif = q.getItem().toString();
                        }
                    } else {
                        objectif = q.getEntityType().toString();
                    }
                }
                im.setDisplayName(main.getConfig().getString("quests-menu.quest-done.title")
                        .replaceAll("&", "§")
                        .replaceAll("%action%", q.getQuestAction().toString().replaceAll("_", " "))
                        .replaceAll("%amount%", ""+q.getAmount())
                        .replaceAll("%objectif%", objectif.replaceAll("_", " ")));
                doneQuestIt.setItemMeta(im);
                inv.setItem(emplacement[i], doneQuestIt);
            } else if (i==questsDone.size()) {
                if (activeQuest.getQuestAction() == QuestAction.POSER) {
                    objectif = "BLOCKS";
                } else {
                    if (activeQuest.getEntityType() == null) {
                        if (activeQuest.getData() != 0) {
                            ItemStack obj = new ItemStack(activeQuest.getItem(), 1, activeQuest.getData());
                            objectif = CraftItemStack.asNMSCopy(obj).getName().toUpperCase();
                        } else {
                            objectif = activeQuest.getItem().toString();
                        }
                    } else {
                        objectif = activeQuest.getEntityType().toString();
                    }
                }
                im = activeQuestIt.getItemMeta();
                im.setDisplayName(main.getConfig().getString("quests-menu.ongoing-quest.title").replaceAll("&", "§")
                        .replaceAll("%action%", activeQuest.getQuestAction().toString().replaceAll("_", " "))
                        .replaceAll("%amount%", ""+activeQuest.getAmount())
                        .replaceAll("%objectif%", objectif.replaceAll("_", " ")));
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

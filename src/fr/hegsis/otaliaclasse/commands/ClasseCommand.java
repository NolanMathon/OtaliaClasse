package fr.hegsis.otaliaclasse.commands;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ClasseCommand  implements CommandExecutor {

    private Main main;
    public ClasseCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSeul un joueur peut faire cette commande !");
            return false;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            if (!main.playersProfile.containsKey(p.getName())) {
                p.openInventory(main.firstChoose);
                return true;
            }

            openCategoriesMenu(p, main);
            p.openInventory(main.classes.get(main.playersProfile.get(p.getName()).getClasseType()).getClasseInventory());
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!p.hasPermission("otaliaclasse.reload")) {
                    main.sendMessage("no-permission", p, "/classe reload");
                    return false;
                }

                // A fix
                main.getPluginLoader().disablePlugin(main);
                main.getPluginLoader().enablePlugin(main);
                return true;
            }

            if (args[0].equalsIgnoreCase("help")) {
                if (!p.hasPermission("otaliaclasse.help")) {
                    main.sendMessage("no-permission", p, "/classe help");
                    return false;
                }

                p.sendMessage("§7§m---------------§6 Classe §7§m---------------");
                p.sendMessage("");
                p.sendMessage("§8• §e/classe §f→ §7Menu des classes");
                p.sendMessage("§8• §e/classe rewards §f→ §7Menu des récompenses");
                p.sendMessage("§8• §e/classe reload §f→ §7Reload le plugin");
                p.sendMessage("§8• §e/classe help §f→ §7Affiche cette aide");
                p.sendMessage("");
                p.sendMessage("§7§m---------------§6 Classe §7§m---------------");
            }
        }


        return false;
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

        Inventory inv = main.classes.get(main.playersProfile.get(p.getName()).getClasseType()).getClasseInventory();

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
}

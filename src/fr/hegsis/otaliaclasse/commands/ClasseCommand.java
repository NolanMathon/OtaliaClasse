package fr.hegsis.otaliaclasse.commands;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestType;
import fr.hegsis.otaliaclasse.utils.OpenInventories;
import org.bukkit.Bukkit;
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

            OpenInventories.openCategoriesMenu(p, main);
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("rewards")) {
                if (!main.playersProfile.containsKey(p.getName())) {
                    p.openInventory(main.firstChoose);
                    return true;
                }

                OpenInventories.openRewardsMenu(p, main);
                return true;
            }

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
}

package fr.hegsis.otaliaclasse.commands;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.utils.OpenInventories;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage("§7§m---------------§6 Classe §7§m---------------");
                p.sendMessage("");
                p.sendMessage("§8• §e/classe §f→ §7Menu des classes");
                p.sendMessage("§8• §e/classe rewards §f→ §7Menu des récompenses");
                p.sendMessage("§8• §e/classe help §f→ §7Affiche cette aide");
                p.sendMessage("");
                p.sendMessage("§7§m---------------§6 Classe §7§m---------------");
            }
        }


        return false;
    }
}

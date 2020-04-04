package fr.hegsis.otaliaclasse.commands;

import fr.hegsis.otaliaclasse.Main;
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

        if (main.pirate.getPlayerList().contains(p.getName()) || main.titan.getPlayerList().contains(p.getName())) {
            p.openInventory(main.firstChoose);
        }
        return true;
    }
}

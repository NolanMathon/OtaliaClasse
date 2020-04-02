package fr.hegsis.otaliaclasse.commands;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClasseCommand  implements CommandExecutor {

    private Main main;
    public ClasseCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        return false;
    }
}

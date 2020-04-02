package fr.hegsis.otaliaclasse;

import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.quests.Quest;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public Map<Integer, Quest> quests = new HashMap<>();

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");

        // Dossier du plugin
        if (!getDataFolder().exists()) { getDataFolder().mkdir(); }

        // Commande /classe
        getCommand("classe").setExecutor(new ClasseCommand(this));
    }
}

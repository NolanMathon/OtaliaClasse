package fr.hegsis.otaliaclasse;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");

        // Dossier du plugin
        if (!getDataFolder().exists()) { getDataFolder().mkdir(); }


        getCommand("classe").setExecutor(new ClasseCommand(this));
    }
}
package fr.hegsis.otaliaclasse;

import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.listeners.*;
import fr.hegsis.otaliaclasse.utils.SetDefaultInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public Classe pirate;
    public Classe titan;
    public Inventory firstChoose;

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");

        // Dossier du plugin
        if (!getDataFolder().exists()) { getDataFolder().mkdir(); }

        registerListeners();

        // Commande /classe
        getCommand("classe").setExecutor(new ClasseCommand(this));
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(this), this);
        pm.registerEvents(new QuestBreakListeners(this), this);
        pm.registerEvents(new QuestCraftListeners(this), this);
        pm.registerEvents(new QuestHarvestListeners(this), this);
        pm.registerEvents(new QuestKillListeners(this), this);
        pm.registerEvents(new QuestPlaceListeners(this), this);
    }

    private void setInventories() {
        firstChoose = SetDefaultInventory.setDefaultChooseInventory(this);
    }
}

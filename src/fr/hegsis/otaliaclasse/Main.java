package fr.hegsis.otaliaclasse;

import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.listeners.*;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.utils.SetDefaultInventory;
import fr.hegsis.otaliaclasse.utils.file.json.ProfileSerializationManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private ProfileSerializationManager profileSerializationManager;

    public Classe pirate;
    public Classe titan;
    public Inventory firstChoose;
    public Map<String, Profile> playersProfile = new HashMap<>();

    @Override
    public void onEnable() {
        this.profileSerializationManager = new ProfileSerializationManager();

        // Dossier du plugin
        if (!getDataFolder().exists()) { getDataFolder().mkdir(); }
        saveDefaultConfig();

        registerListeners();
        setInventories();

        // Commande /classe
        getCommand("classe").setExecutor(new ClasseCommand(this));

        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(this), this);
        pm.registerEvents(new JoinQuitListeners(this), this);
        pm.registerEvents(new QuestBreakListeners(this), this);
        pm.registerEvents(new QuestCraftListeners(this), this);
        pm.registerEvents(new QuestHarvestListeners(this), this);
        pm.registerEvents(new QuestKillListeners(this), this);
        pm.registerEvents(new QuestPlaceListeners(this), this);
    }

    private void setInventories() {
        firstChoose = SetDefaultInventory.setDefaultChooseInventory(this);
    }

    public ProfileSerializationManager getProfileSerializationManager() {
        return this.profileSerializationManager;
    }
}

package fr.hegsis.otaliaclasse;

import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.listeners.*;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.utils.SetDefaultInventory;
import fr.hegsis.otaliaclasse.utils.SetQuestsList;
import fr.hegsis.otaliaclasse.utils.file.json.JsonFileUtils;
import fr.hegsis.otaliaclasse.utils.file.json.ProfileSerializationManager;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFileUtils;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() { return instance; }

    private ProfileSerializationManager profileSerializationManager;

    public Map<ClasseType, Classe> classes = new HashMap<>();
    public Inventory firstChoose;
    public Map<String, Profile> playersProfile = new HashMap<>();

    @Override
    public void onEnable() {
        this.profileSerializationManager = new ProfileSerializationManager();
        instance = this;

        saveDefaultFiles(); // Méthode qui permet l'enregistrement des fichiers par défaut s'il n'existent pas

        registerListeners(); // Méthode qui permet l'enregistrement des class d'events
        createClasses(); // Méthode qui permet de créer les deux classes
        setInventories(); // Méthode qui permet de set tous les inventaires

        List<Quest> questsList = SetQuestsList.setTitanQuests();

        // Commande /classe
        getCommand("classe").setExecutor(new ClasseCommand(this));

        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");
    }

    private void saveDefaultFiles() {
        saveDefaultConfig();
        if(!YamlFileUtils .getFile(YamlFiles.TITAN_QUESTS).exists()) {
            saveResource("titan_quests.yml", false);
        }

        if(!YamlFileUtils .getFile(YamlFiles.PIRATE_QUESTS).exists()) {
            saveResource("pirate_quests.yml", false);
        }
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

    private void createClasses() {
        this.classes.put(ClasseType.PIRATE, new Classe(new HashMap<>(), new ArrayList<>(), ClasseType.PIRATE));
        this.classes.put(ClasseType.TITAN, new Classe(new HashMap<>(), new ArrayList<>(), ClasseType.TITAN));
    }

    public ProfileSerializationManager getProfileSerializationManager() {
        return this.profileSerializationManager;
    }

    public void saveProfileOnJson(Player p, Profile profile) {
        File file = new File(new File(getDataFolder(), "/profiles/"), p.getName() + ".json");

        ProfileSerializationManager profileSerializationManager = getProfileSerializationManager();
        String json = profileSerializationManager.serialize(profile);

        JsonFileUtils.saveJson(file, json);
    }

    public void sendMessage(String path, Player p, String replacement) {
        p.sendMessage(getConfig().getString("messages." + path).replaceAll("%data%", replacement).replaceAll("&", "§"));
    }
}

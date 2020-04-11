package fr.hegsis.otaliaclasse;

import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.commands.ClasseCommand;
import fr.hegsis.otaliaclasse.listeners.*;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.utils.SetDefaultInventory;
import fr.hegsis.otaliaclasse.utils.SetQuestsList;
import fr.hegsis.otaliaclasse.utils.SetRewardsList;
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
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() { return instance; }

    private ProfileSerializationManager profileSerializationManager;

    public Inventory firstChoose;
    public Inventory rewardMenu;

    public Map<ClasseType, Classe> classes = new HashMap<>();
    public Map<String, Profile> playersProfile = new HashMap<>();

    @Override
    public void onEnable() {
        this.profileSerializationManager = new ProfileSerializationManager();
        instance = this;

        saveDefaultFiles(); // Méthode qui permet l'enregistrement des fichiers par défaut s'il n'existent pas

        registerListeners(); // Méthode qui permet l'enregistrement des class d'events
        createClasses(); // Méthode qui permet de créer les deux classes
        setInventories(); // Méthode qui permet de set tous les inventaires

        if (Bukkit.getOnlinePlayers().size() >= 1) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                loadProfileFromJson(p);
            }
        }

        // Commande /classe
        getCommand("classe").setExecutor(new ClasseCommand(this));

        this.getServer().getConsoleSender().sendMessage("§7OtaliaClasse §5→ §aON §f§l(By HegSiS)");
    }

    @Override
    public void onDisable() {
        // On sauvegarde la progression de tous les joueurs
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (playersProfile.containsKey(p.getName())) {
                    saveProfileOnJson(p, playersProfile.get(p.getName()));
                    playersProfile.remove(p.getName());
                }
            }
        }
    }

    private void saveDefaultFiles() {
        saveDefaultConfig();
        for (YamlFiles yamlFiles : YamlFiles.values()) {
            if(!YamlFileUtils .getFile(yamlFiles).exists()) {
                saveResource(yamlFiles.toString().toLowerCase()+".yml", false);
            }
        }
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(this), this);
        pm.registerEvents(new JoinQuitListeners(this), this);
        pm.registerEvents(new QuestBreakListeners(this), this);
        pm.registerEvents(new QuestConsumeListeners(this), this);
        pm.registerEvents(new QuestCraftListeners(this), this);
        pm.registerEvents(new QuestFishListeners(this), this);
        pm.registerEvents(new QuestHarvestListeners(this), this);
        pm.registerEvents(new QuestKillListeners(this), this);
        pm.registerEvents(new QuestPlaceListeners(this), this);
    }

    private void setInventories() {
        firstChoose = SetDefaultInventory.setDefaultChooseInventory(this);
        classes.get(ClasseType.PIRATE).setClasseInventory(SetDefaultInventory.setDefaultClasseMenuInventory(this, ClasseType.PIRATE));
        classes.get(ClasseType.TITAN).setClasseInventory(SetDefaultInventory.setDefaultClasseMenuInventory(this, ClasseType.TITAN));
        rewardMenu = SetDefaultInventory.setDefaultRewardMenu(this);
    }

    private void createClasses() {
        classes.put(ClasseType.PIRATE, new Classe(SetQuestsList.setQuestList(YamlFiles.PIRATE_QUESTS), ClasseType.PIRATE, SetRewardsList.setRewardsList(YamlFiles.PIRATE_REWARDS)));
        classes.put(ClasseType.TITAN, new Classe(SetQuestsList.setQuestList(YamlFiles.TITAN_QUESTS), ClasseType.TITAN, SetRewardsList.setRewardsList(YamlFiles.TITAN_REWARDS)));
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

    public void loadProfileFromJson(Player p) {
        File file = new File(new File(getDataFolder(), "/profiles/"), p.getName() + ".json");

        if (file.exists()) {
            ProfileSerializationManager profileSerializationManager = getProfileSerializationManager();
            String json = JsonFileUtils.loadJson(file);
            Profile profile = profileSerializationManager.deserialize(json);
            playersProfile.put(p.getName(), profile);
        }
    }

    public void sendMessage(String path, Player p, String replacement) {
        p.sendMessage(getConfig().getString("messages." + path).replaceAll("%data%", replacement).replaceAll("&", "§"));
    }
}

package fr.hegsis.otaliaclasse.classes;

import fr.hegsis.otaliaclasse.quests.Quest;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class Classe {
    private List<Quest> questList;
    private List<String> playerList;
    private ClasseType classeType;
    private Inventory classeInventory;

    public Classe(List<Quest> questList, List<String> playerList, ClasseType classeType) {
        this.questList = questList;
        this.playerList = playerList;
        this.classeType = classeType;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void addPlayer(String s) {
        playerList.add(s);
    }

    public void removePlayer(String s) {
        playerList.remove(s);
    }

    public boolean containPlayer(String s) {
        return playerList.contains(s);
    }

    public ClasseType getClasseType() {
        return classeType;
    }

    public void setClasseInventory(Inventory classeInventory) {
        this.classeInventory = classeInventory;
    }

    public Inventory getClasseInventory() {
        return classeInventory;
    }
}

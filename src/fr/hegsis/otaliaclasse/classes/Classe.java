package fr.hegsis.otaliaclasse.classes;

import fr.hegsis.otaliaclasse.quests.Quest;

import java.util.List;
import java.util.Map;

public class Classe {
    private Map<Integer, Quest> questMap;
    private List<String> playerList;
    private ClasseType classeType;

    public Classe(Map<Integer, Quest> questMap, List<String> playerList, ClasseType classeType) {
        this.questMap = questMap;
        this.playerList = playerList;
        this.classeType = classeType;
    }

    public Map<Integer, Quest> getQuestMap() {
        return questMap;
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
}

package fr.hegsis.otaliaclasse.classes;

import fr.hegsis.otaliaclasse.quests.Quest;

import java.util.List;
import java.util.Map;

public class Classe {
    private Map<Integer, Quest> questMap;
    private List<String> playerList;

    public Classe(Map<Integer, Quest> questMap, List<String> playerList) {
        this.questMap = questMap;
        this.playerList = playerList;
    }

    public Map<Integer, Quest> getQuestMap() {
        return questMap;
    }

    public List<String> getPlayerList() {
        return playerList;
    }
}

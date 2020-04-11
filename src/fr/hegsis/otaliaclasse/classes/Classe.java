package fr.hegsis.otaliaclasse.classes;

import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.rewards.Reward;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

public class Classe {
    private List<Quest> questList;
    private ClasseType classeType;
    private Inventory classeInventory;
    private Map<Integer, Reward> classeRewards;

    public Classe(List<Quest> questList, ClasseType classeType, Map<Integer, Reward> classeRewards) {
        this.questList = questList;
        this.classeType = classeType;
        this.classeRewards = classeRewards;
    }

    public List<Quest> getQuestList() {
        return questList;
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

    public Map<Integer, Reward> getClasseRewards() {
        return classeRewards;
    }

    public Reward getClassRewardsByLevel(int level) {
        return  classeRewards.get(level);
    }
}

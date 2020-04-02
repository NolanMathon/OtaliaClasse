package fr.hegsis.otaliaclasse.quests;

import org.bukkit.Material;

public class Quest {
    private int id;
    private QuestType questType;
    private int amount;
    private Material item;

    public Quest(int id, QuestType questType, int amount, Material item) {
        this.id = id;
        this.questType = questType;
        this.amount = amount;
        this.item = item;
    }

    public Quest(int id, QuestType questType, int amount) {
        this.id = id;
        this.questType = questType;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public int getAmount() {
        return amount;
    }

    public Material getItem() {
        return item;
    }
}

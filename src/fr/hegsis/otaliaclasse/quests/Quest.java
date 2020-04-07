package fr.hegsis.otaliaclasse.quests;

import fr.hegsis.otaliaclasse.classes.ClasseType;
import org.bukkit.Material;

public class Quest {
    private int id;
    private ClasseType classeType;
    private QuestAction questAction;
    private int amount;
    private Material item;
    private int expRewards;

    public Quest(int id, ClasseType classeType, QuestAction questAction, int amount, Material item, int expRewards) {
        this.id = id;
        this.classeType = classeType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = item;
        this.expRewards = expRewards;
    }

    public Quest(int id, ClasseType classeType, QuestAction questAction, int amount, int expRewards) {
        this.id = id;
        this.classeType = classeType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = null;
        this.expRewards = expRewards;
    }

    public int getId() {
        return id;
    }

    public ClasseType getClasseType() {
        return classeType;
    }

    public QuestAction getQuestType() {
        return questAction;
    }

    public int getAmount() {
        return amount;
    }

    public Material getItem() {
        return item;
    }

    public QuestAction getQuestAction() {
        return questAction;
    }

    public int getExpRewards() {
        return expRewards;
    }
}

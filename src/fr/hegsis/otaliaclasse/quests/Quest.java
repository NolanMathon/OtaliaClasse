package fr.hegsis.otaliaclasse.quests;

import fr.hegsis.otaliaclasse.classes.ClasseType;
import org.bukkit.Material;

public class Quest {
    private int id;
    private ClasseType classeType;
    private QuestAction questAction;
    private int amount;
    private Material item;

    public Quest(int id, ClasseType classeType, QuestAction questAction, int amount, Material item) {
        this.id = id;
        this.classeType = classeType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = item;
    }

    public Quest(int id, ClasseType classeType, QuestAction questAction, int amount) {
        this.id = id;
        this.classeType = classeType;
        this.questAction = questAction;
        this.amount = amount;
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
}

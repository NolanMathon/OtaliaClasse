package fr.hegsis.otaliaclasse.quests;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class Quest {
    private int id;
    private QuestType questType;
    private QuestAction questAction;
    private int amount;
    private Material item;
    private short data;
    private int expRewards;
    private EntityType entityType;

    public Quest(int id, QuestType questType, QuestAction questAction, int amount, Material item, int expRewards) {
        this.id = id;
        this.questType = questType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = item;
        this.entityType = null;
        this.expRewards = expRewards;
    }

    public Quest(int id, QuestType questType, QuestAction questAction, int amount, Material item, short data, int expRewards) {
        this.id = id;
        this.questType = questType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = item;
        this.data = data;
        this.entityType = null;
        this.expRewards = expRewards;
    }

    public Quest(int id, QuestType questType, QuestAction questAction, int amount, int expRewards) {
        this.id = id;
        this.questType = questType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = null;
        this.entityType = null;
        this.expRewards = expRewards;
    }

    public Quest(int id, QuestType questType, QuestAction questAction, EntityType entityType, int expRewards) {
        this.id = id;
        this.questType = questType;
        this.questAction = questAction;
        this.amount = amount;
        this.item = null;
        this.entityType = entityType;
        this.expRewards = expRewards;
    }

    public int getId() {
        return id;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public QuestAction getQuestAction() {
        return questAction;
    }

    public int getAmount() {
        return amount;
    }

    public Material getItem() {
        return item;
    }

    public int getExpRewards() {
        return expRewards;
    }

    public short getData() {
        return data;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}

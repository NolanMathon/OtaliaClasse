package fr.hegsis.otaliaclasse.profiles;

import fr.hegsis.otaliaclasse.classes.ClasseType;

import java.util.UUID;

public class Profile {
    private UUID uuid;
    private String userName;
    private ClasseType classeType;
    private int classLevel;
    private int classExp;
    private int[] doneQuestId;
    private int[] activeQuestId;
    private int progressionQuest;

    public Profile(UUID uuid, String userName, ClasseType classeType, int classLevel, int classExp, int[] doneQuestId, int[] activeQuestId, int progressionQuest) {
        this.uuid = uuid;
        this.userName = userName;
        this.classeType = classeType;
        this.classLevel = classLevel;
        this.classExp = classExp;
        this.doneQuestId = doneQuestId;
        this.activeQuestId = activeQuestId;
        this.progressionQuest = progressionQuest;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ClasseType getClasseType() {
        return classeType;
    }

    public void setClasseType(ClasseType classeType) {
        this.classeType = classeType;
    }

    public int getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }

    public int getClassExp() {
        return classExp;
    }

    public void setClassExp(int classExp) {
        this.classExp = classExp;
    }

    public int[] getDoneQuestId() {
        return doneQuestId;
    }

    public void setDoneQuestId(int[] doneQuestId) {
        this.doneQuestId = doneQuestId;
    }

    public int[] getActiveQuestId() {
        return activeQuestId;
    }

    public void setActiveQuestId(int[] activeQuestId) {
        this.activeQuestId = activeQuestId;
    }

    public int getProgressionQuest() {
        return progressionQuest;
    }

    public void setProgressionQuest(int progressionQuest) {
        this.progressionQuest = progressionQuest;
    }
}

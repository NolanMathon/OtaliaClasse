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
    private int[] progressionQuest;

    public Profile(UUID uuid, String userName, ClasseType classeType, int classLevel, int classExp, int[] doneQuestId, int[] activeQuestId, int[] progressionQuest) {
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

    public void setProgressionQuest(int[] progressionQuest) {
        this.progressionQuest = progressionQuest;
    }

    public int[] getProgressionQuest() {
        return progressionQuest;
    }

    public void addDoneQuestId(int emplacement) {
        int questId = this.activeQuestId[emplacement];
        int[] newDoneQuestId = new int[doneQuestId.length + 1];
        for (int i=0; i<doneQuestId.length; i++) {
            newDoneQuestId[i] = doneQuestId[i];
        }
        newDoneQuestId[doneQuestId.length] = questId;
        this.doneQuestId = newDoneQuestId;
    }

    public void setQuestProgression(int emplacement, int newProgression) {
        this.progressionQuest[emplacement] = newProgression;
    }

    public int getQuestProgression(int emplacement) {
        return this.progressionQuest[emplacement];
    }

    public void setNewActiveQuestId(int emplacement, int newQuestId) {
        this.activeQuestId[emplacement] = newQuestId;
    }

    public void removeActiveQuestId(int emplacement) {
        int[] newActiveQuestId = new int[activeQuestId.length - 1];
        for (int i=0; i<activeQuestId.length; i++) {
            if (i > emplacement) {
                newActiveQuestId[i-1] = activeQuestId[i];
            }

            if (i < emplacement) {
                newActiveQuestId[i] = activeQuestId[i];
            }
        }
        this.activeQuestId = newActiveQuestId;
    }


    public void removeQuestProgression(int emplacement) {
        int[] newQuestProgression = new int[progressionQuest.length - 1];
        for (int i=0; i<progressionQuest.length; i++) {
            if (i > emplacement) {
                newQuestProgression[i-1] = progressionQuest[i];
            }

            if (i < emplacement) {
                newQuestProgression[i] = progressionQuest[i];
            }
        }
        this.progressionQuest = newQuestProgression;
    }



}

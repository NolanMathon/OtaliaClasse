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
    private int[] rewardsClaimed;

    public Profile(UUID uuid, String userName, ClasseType classeType, int classLevel, int classExp, int[] doneQuestId, int[] activeQuestId, int[] progressionQuest, int[] rewardsClaimed) {
        this.uuid = uuid;
        this.userName = userName;
        this.classeType = classeType;
        this.classLevel = classLevel;
        this.classExp = classExp;
        this.doneQuestId = doneQuestId;
        this.activeQuestId = activeQuestId;
        this.progressionQuest = progressionQuest;
        this.rewardsClaimed = rewardsClaimed;
    }

    public ClasseType getClasseType() {
        return classeType;
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

    public int[] getActiveQuestId() {
        return activeQuestId;
    }

    public int[] getProgressionQuest() {
        return progressionQuest;
    }

    public void addDoneQuestId(int emplacement) {
        int questId = this.activeQuestId[emplacement];
        int length = 0;
        if (doneQuestId != null) {
            length = doneQuestId.length;
        }
        int[] newDoneQuestId = new int[length + 1];
        for (int i=0; i<length; i++) {
            newDoneQuestId[i] = doneQuestId[i];
        }
        newDoneQuestId[length] = questId;
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

    public int[] getRewardsClaimed() {
        return rewardsClaimed;
    }

    public void addRewardsClaimed(int rewardLevel) {
        int length = 0;
        if (this.rewardsClaimed != null) {
            length = this.rewardsClaimed.length;
        }
        int[] rewardsClaimed = new int[length+1];
        for (int i=0; i<length; i++) {
            rewardsClaimed[i] = this.rewardsClaimed[i];
        }
        rewardsClaimed[length] = rewardLevel;
        this.rewardsClaimed = rewardsClaimed;
    }
}

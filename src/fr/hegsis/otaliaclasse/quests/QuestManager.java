package fr.hegsis.otaliaclasse.quests;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.utils.Utils;
import org.bukkit.entity.Player;

public class QuestManager {

    public static void addOneToQuestProgression(Player p, Profile profile, Quest q, int emplacement, Main main) {
        profile.setQuestProgression(emplacement, profile.getQuestProgression(emplacement)+1);
        if ((profile.getQuestProgression(emplacement)%100) == 0) {
            main.saveProfileOnJson(p, profile);
        }
        main.playersProfile.replace(p.getName(), isQuestDone(p, profile, q, emplacement, main));
    }

    private static Profile isQuestDone(Player p, Profile profile, Quest q, int emplacement, Main main) {
        int objectif = q.getAmount();
        int progression = profile.getQuestProgression(emplacement);

        if (progression < objectif) return profile;

        profile.addDoneQuestId(emplacement);

        Classe classe = main.classes.get(profile.getClasseType());
        if (classe.getQuestList().get(q.getId()).getQuestType() == q.getQuestType()) {
            profile.setNewActiveQuestId(emplacement, classe.getQuestList().get(q.getId()).getId());
            profile.setQuestProgression(emplacement, 0);
        } else {
            profile.removeActiveQuestId(emplacement);
            profile.removeQuestProgression(emplacement);
        }

        questDoneEvent(p, q, main);
        profile.setClassExp(profile.getClassExp()+q.getExpRewards());

        Profile newProfile = isLevelingUp(p, profile, main);
        main.saveProfileOnJson(p, newProfile);
        return newProfile;
    }

    private static Profile isLevelingUp(Player p, Profile profile, Main main) {
        int playerClassExp = profile.getClassExp();
        int playerClassLevel = profile.getClassLevel();
        int necessaryExp = main.getConfig().getInt("necessary-xp."+playerClassLevel+"to"+(playerClassLevel+1));

        if (playerClassExp < necessaryExp || playerClassLevel == 6) return profile;

        int newLevelExp = playerClassExp - necessaryExp;
        profile.setClassExp(newLevelExp);
        profile.setClassLevel(playerClassLevel+1);

        levelUpEvent(p, profile, main);

        return profile;
    }

    private static void questDoneEvent(Player p, Quest q, Main main) {
        String objectif;
        if (q.getQuestAction() == QuestAction.POSER) {
            objectif = "Blocks";
        } else {
            if (q.getEntityType() == null) {
                objectif = q.getItem().toString();
            } else {
                objectif = q.getEntityType().toString();
            }
        }
        Utils.sendTitle(p, main.getConfig().getString("title.quest-done.title").replaceAll("&", "§"),
                main.getConfig().getString("title.quest-done.subtitle")
                        .replaceAll("&", "§")
                        .replaceAll("%action%", ""+q.getQuestAction())
                        .replaceAll("%amount%", ""+q.getAmount())
                        .replaceAll("%objectif%", objectif));
    }

    private static void levelUpEvent(Player p, Profile profile, Main main) {
        Utils.sendTitle(p, main.getConfig().getString("title.level-up.title").replaceAll("&", "§").replaceAll("%lvl%", ""+profile.getClassLevel()), main.getConfig().getString("title.level-up.subtitle").replaceAll("&", "§"));
    }
}

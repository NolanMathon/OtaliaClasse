package fr.hegsis.otaliaclasse.classes;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.rewards.Reward;
import fr.hegsis.otaliaclasse.utils.OpenInventories;
import org.bukkit.entity.Player;

public class ClassManager {

    public static void giveReward(Player p, int level, Main main) {
        Profile profile = main.playersProfile.get(p.getName());
        int playerClassLevel = profile.getClassLevel();

        // Si le joueur n'a pas encore atteint le level
        if (playerClassLevel < level) return;

        int[] rewardsClaimed = profile.getRewardsClaimed();

        if (rewardsClaimed != null) {
            // On check si le joueur n'a pas déjà récupérer la récompense !
            for (int r : rewardsClaimed) {
                if (r == level) return;
            }
        }

        profile.addRewardsClaimed(level);
        main.playersProfile.replace(p.getName(), profile);
        main.saveProfileOnJson(p, profile);

        Classe classe = main.classes.get(profile.getClasseType());
        Reward reward = classe.getClassRewardsByLevel(level);
        reward.give(p);
        OpenInventories.openRewardsMenu(p, main);
    }
}

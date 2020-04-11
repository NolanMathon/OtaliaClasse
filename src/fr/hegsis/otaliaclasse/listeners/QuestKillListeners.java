package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class QuestKillListeners implements Listener {

    private Main main;
    public QuestKillListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerKillForQuest(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (main.playersProfile.containsKey(p.getName())) {
            Profile profile = main.playersProfile.get(p.getName());
            Classe classe = main.classes.get(profile.getClasseType());
            List<Quest> questList = classe.getQuestList();

            for (int i=0; i<profile.getActiveQuestId().length; i++) {
                Quest q = questList.get(profile.getActiveQuestId()[i]-1);
                if (q.getQuestAction() == QuestAction.TUER_A_LA_SUITE){
                    QuestManager.resetQuestProgression(p, profile, q, i, main);
                }
            }
        }

        if (p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) p.getLastDamageCause();

            if (event.getDamager() instanceof Player) {
                Player killer = (Player) event.getDamager();
                checkAndAddOneProgression(killer);
                return;
            }

            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    Player killer = (Player) arrow.getShooter();
                    checkAndAddOneProgression(killer);
                    return;
                }
            }
        }
    }

    private void checkAndAddOneProgression(Player p) {
        if (!main.playersProfile.containsKey(p.getName())) return;

        Profile profile = main.playersProfile.get(p.getName());
        Classe classe = main.classes.get(profile.getClasseType());
        List<Quest> questList = classe.getQuestList();

        for (int i=0; i<profile.getActiveQuestId().length; i++) {
            Quest q = questList.get(profile.getActiveQuestId()[i]-1);
            if (q.getQuestAction() == QuestAction.TUER || q.getQuestAction() == QuestAction.TUER_A_LA_SUITE){
                QuestManager.addOneToQuestProgression(p, profile, q, i, main);
            }
        }
    }

}

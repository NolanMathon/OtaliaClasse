package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class QuestBreakListeners implements Listener {

    private Main main;
    public QuestBreakListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBreakForQuest(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        if (!main.playersProfile.containsKey(p.getName())) return;

        Profile profile = main.playersProfile.get(p.getName());
        Classe classe = main.classes.get(profile.getClasseType());
        List<Quest> questList = classe.getQuestList();

        Block breakItem = e.getBlock();
        Material itemToBreak;

        for (int i=0; i<profile.getActiveQuestId().length; i++) {
            Quest q = questList.get(profile.getActiveQuestId()[i]-1);
            if (q.getQuestAction() == QuestAction.CASSER){
                itemToBreak = q.getItem();

                if (breakItem.getType() == itemToBreak) {
                    QuestManager.addOneToQuestProgression(p, profile, q, i, main);
                }
            }
        }
    }

}

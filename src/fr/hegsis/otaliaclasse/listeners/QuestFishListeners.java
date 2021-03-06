package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestFishListeners implements Listener {

    private Main main;
    public QuestFishListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerFishForQuest(PlayerFishEvent e) {
        if (e.isCancelled()) return;

        if(!(e.getCaught() instanceof Item))return;

        Player p = e.getPlayer();
        if (!main.playersProfile.containsKey(p.getName())) return;

        Profile profile = main.playersProfile.get(p.getName());
        Classe classe = main.classes.get(profile.getClasseType());
        List<Quest> questList = classe.getQuestList();

        ItemStack fishItem = ((Item) e.getCaught()).getItemStack();
        Material itemToFish;
        short data;

        for (int i=0; i<profile.getActiveQuestId().length; i++) {
            Quest q = questList.get(profile.getActiveQuestId()[i]-1);
            if (q.getQuestAction() == QuestAction.PECHER){
                itemToFish = q.getItem();
                data = q.getData();
                if (fishItem.getType() == itemToFish && fishItem.getDurability() == data) {
                    QuestManager.addOneToQuestProgression(p, profile, q, i, main);
                }
            }
        }
    }
}

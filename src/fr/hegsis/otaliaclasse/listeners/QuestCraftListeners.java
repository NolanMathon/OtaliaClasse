package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestCraftListeners implements Listener {

    private Main main;
    public QuestCraftListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCraftForQuest(CraftItemEvent e) {
        if (e.isCancelled()) return;

        if (e.getRecipe().getResult().getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();
        if (!main.playersProfile.containsKey(p.getName())) return;

        Profile profile = main.playersProfile.get(p.getName());
        Classe classe = main.classes.get(profile.getClasseType());
        List<Quest> questList = classe.getQuestList();

        ItemStack craftItem = e.getRecipe().getResult();
        Material itemToCraft;
        short data;

        for (int j=0; j<getAmountCraftItem(craftItem.getType(), e); j++) {
            profile = main.playersProfile.get(p.getName());
            for (int i=0; i<profile.getActiveQuestId().length; i++) {
                Quest q = questList.get(profile.getActiveQuestId()[i]-1);
                if (q.getQuestAction() == QuestAction.FABRIQUER){
                    itemToCraft = q.getItem();
                    data = q.getData();
                    if (craftItem.getType() == itemToCraft && craftItem.getDurability() == data) {
                        QuestManager.addOneToQuestProgression(p, profile, q, i, main);
                    }
                }
            }
        }
    }

    private int getAmountCraftItem(Material m, CraftItemEvent e){
        if (e.isCancelled()) return 0;

        if(!e.getRecipe().getResult().getType().equals(m)) return 0;

        int amount = e.getRecipe().getResult().getAmount();

        if (e.isShiftClick()) {
            int max = e.getInventory().getMaxStackSize();
            ItemStack[] matrix = e.getInventory().getMatrix();
            for (ItemStack is: matrix) {
                if (is == null || is.getType().equals(Material.AIR)) continue;
                int tmp = is.getAmount();
                if (tmp < max && tmp > 0) max = tmp;
            }
            amount *= max;
        }
        return amount;
    }
}

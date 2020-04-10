package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestManager;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.List;

public class QuestHarvestListeners implements Listener {

    private Main main;
    public QuestHarvestListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onHarvestForQuest(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        if (!main.playersProfile.containsKey(p.getName())) return;

        Profile profile = main.playersProfile.get(p.getName());
        Classe classe = main.classes.get(profile.getClasseType());
        List<Quest> questList = classe.getQuestList();

        Block harvestItem = e.getBlock();
        Material itemToHarvest;

        for (int i=0; i<profile.getActiveQuestId().length; i++) {
            Quest q = questList.get(profile.getActiveQuestId()[i]-1);
            if (q.getQuestAction() == QuestAction.RECOLTER){
                itemToHarvest = q.getItem();
                if (harvestItem.getType() == itemToHarvest) {
                    if (isMaturityPlant(harvestItem, p)) {
                        QuestManager.addOneToQuestProgression(p, profile, q, i, main);
                    }
                }
            }
        }
    }

    private boolean isMaturityPlant(Block b, Player p) {
        boolean isMature = true;

        switch (b.getType()) {
            case MELON_BLOCK:
                    if (p.getItemInHand() != null
                            && p.getItemInHand().hasItemMeta()
                            && p.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                        isMature = false;
                    }
                    break;
            case COCOA:
                CocoaPlant cocoaPlant = (CocoaPlant) b.getState().getData();
                if (cocoaPlant.getSize() != CocoaPlant.CocoaPlantSize.LARGE) isMature = false;
                break;
            case CARROT:
                if (!b.getState().getData().toString().equalsIgnoreCase(b.getType().toString()+"(7)")) isMature = false;
                break;
            case CROPS:
                Crops crop = (Crops) b.getState().getData();
                if (crop.getState() != CropState.RIPE) isMature = false;
                break;
            case NETHER_WARTS:
                NetherWarts netherWarts = (NetherWarts) b.getState().getData();
                if (netherWarts.getState() != NetherWartsState.RIPE) isMature = false;
                break;
            case POTATO:
                if (!b.getState().getData().toString().equalsIgnoreCase(b.getType().toString()+"(7)")) isMature = false;
                break;
        }

        return isMature;
    }

}

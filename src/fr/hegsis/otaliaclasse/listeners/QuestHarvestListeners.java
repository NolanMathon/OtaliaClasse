package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class QuestHarvestListeners implements Listener {

    private Main main;
    public QuestHarvestListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onHarvestForQuest(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
    }

}

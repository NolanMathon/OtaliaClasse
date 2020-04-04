package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class QuestBreakListeners implements Listener {

    private Main main;
    public QuestBreakListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBreakForQuest(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
    }

}

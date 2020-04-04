package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class QuestPlaceListeners implements Listener {

    private Main main;
    public QuestPlaceListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlaceForQuest(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
    }

}

package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class QuestKillListeners implements Listener {

    private Main main;
    public QuestKillListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onKillForQuest(PlayerDeathEvent e) {
        Player p = e.getEntity();
    }

}

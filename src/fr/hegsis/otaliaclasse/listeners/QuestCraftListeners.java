package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class QuestCraftListeners implements Listener {

    private Main main;
    public QuestCraftListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCraftForQuest(PrepareItemCraftEvent e) {
        if (e.getRecipe().getResult().getType() == Material.AIR) return;
    }
}

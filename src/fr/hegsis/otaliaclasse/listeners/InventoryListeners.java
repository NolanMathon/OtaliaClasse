package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    private Main main;
    public InventoryListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        // Si le nom de l'inventaire cliqué est le même que celui du choix entre pirta et titan
        if (e.getInventory().getTitle().equalsIgnoreCase(main.firstChoose.getTitle())) {
            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();

            if (!item.hasItemMeta()) return;

            /*System.out.println(item.getItemMeta().getDisplayName());
            System.out.println(main.firstChoose.getItem(12).getItemMeta().getDisplayName());
            System.out.println(item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName()));*/

            if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName()) && !item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(14).getItemMeta().getDisplayName())) return;

            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName())) {
                main.pirate.addPlayer("HegSiS");
            }

            System.out.println(item.getItemMeta().getDisplayName());

        }

    }

}

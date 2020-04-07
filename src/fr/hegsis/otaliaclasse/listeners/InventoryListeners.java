package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.ClasseType;
import fr.hegsis.otaliaclasse.profiles.Profile;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
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

            if (!item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName()) && !item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(14).getItemMeta().getDisplayName())) return;

            ClasseType classeType = null;
            int[] activeQuests = null;
            int[] progressQuests = null;

            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(12).getItemMeta().getDisplayName())) {
                classeType = ClasseType.PIRATE;
                activeQuests = new int[]{1,11,21,31,41};
                progressQuests = new int[] {0,0,0,0,0};
            }

            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(main.firstChoose.getItem(14).getItemMeta().getDisplayName())) {
                classeType = ClasseType.TITAN;
                activeQuests = new int[]{1,11,21,31};
                progressQuests = new int[] {0,0,0,0};
            }

            for(HumanEntity he:e.getViewers()) {
                if(he instanceof Player) {
                    Player p = (Player) he;
                    main.classes.get(classeType).addPlayer(p.getName());
                    Profile profile = new Profile(p.getUniqueId(), p.getName(), classeType, 0, 0, null, activeQuests, progressQuests);
                    main.sendMessage("join-classe", p, classeType.toString().toLowerCase());
                    main.saveProfileOnJson(p, profile);
                    main.playersProfile.put(p.getName(), profile);
                    p.closeInventory();
                    return;
                }
            }

        }

    }

}

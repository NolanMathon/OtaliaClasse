package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.classes.Classe;
import fr.hegsis.otaliaclasse.profiles.Profile;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Utils {

    public static int isNumber(String entier) {
        try {
            return Integer.parseInt(entier);
        } catch (Exception e) {
            return -1;
        }
    }

    @Deprecated
    public static void sendTitle(Player p, String title, String subtitle) {
        //p.sendTitle(new Title(title, subtitle, 10, 80, 10));
        p.sendTitle(title, subtitle);
    }

    public static void sendFirework(Player p, Color color) {
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder().withColor(color).trail(true).build());
        fw.setFireworkMeta(meta);
    }

    public static ItemStack getGlobalStatsPlayerHead(Player p, Main main) {
        Profile profile = main.playersProfile.get(p.getName());
        int[] doneQuests = profile.getDoneQuestId();
        Classe classe = main.classes.get(profile.getClasseType());

        int totalQuest, doneQuest = 0, progression, exp, expToLevelUp;
        if (doneQuests != null) {
            doneQuest = doneQuests.length;
        }

        totalQuest = classe.getQuestList().size();
        progression = (doneQuest * 100) / totalQuest;
        exp = profile.getClassExp();
        expToLevelUp = main.getConfig().getInt("necessary-xp."+profile.getClassLevel()+"to"+(profile.getClassLevel()+1));

        ItemStack it = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) it.getItemMeta();
        sm.setOwner(p.getName());
        sm.setDisplayName(main.getConfig().getString("global-stats.name").replaceAll("&", "§").replaceAll("%classe%", classe.getClasseType().toString().toLowerCase()).replaceAll("%level%", ""+profile.getClassLevel()));
        List<String> lore = main.getConfig().getStringList("global-stats.description");
        for (int i=0; i<lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§")
                    .replaceAll("%exp%", ""+exp)
                    .replaceAll("%expneeded%", ""+(expToLevelUp-exp))
                    .replaceAll("%done%", ""+doneQuest)
                    .replaceAll("%all%", ""+totalQuest)
                    .replaceAll("%progression%", ""+progression));
        }
        sm.setLore(lore);
        it.setItemMeta(sm);

        return it;
    }
}

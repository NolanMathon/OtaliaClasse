package fr.hegsis.otaliaclasse.utils;

import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

public class Utils {

    public static int isNumber(String entier) {
        try {
            return Integer.parseInt(entier);
        } catch (Exception e) {
            return -1;
        }
    }

    public static void sendTitle(Player p, String title, String subtitle) {
        p.sendTitle(new Title(title, subtitle, 5, 60, 5));
    }
}

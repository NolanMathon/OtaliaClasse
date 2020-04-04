package fr.hegsis.otaliaclasse.utils;

import java.util.List;

public class StringUtils {

    public static List<String> convertLoreColorCode(List<String> lore) {
        for (int i=0; i<lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        return lore;
    }
}

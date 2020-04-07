package fr.hegsis.otaliaclasse.utils;

public class Utils {

    public static int isNumber(String entier) {
        try {
            return Integer.parseInt(entier);
        } catch (Exception e) {
            return -1;
        }
    }
}

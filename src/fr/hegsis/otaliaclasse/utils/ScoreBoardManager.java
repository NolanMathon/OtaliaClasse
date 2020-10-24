package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreBoardManager {

    public static void setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("board", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§lIrélion");

        Profile profile = Main.getInstance().playersProfile.get(player.getName());

        Score void0 = obj.getScore("  ");
        void0.setScore(14);

        Score classe_string = obj.getScore("§6Classe");
        classe_string.setScore(13);
        Team classe = board.registerNewTeam("classe");
        classe.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        classe.setPrefix("Aucune");
        if (profile != null) {
            if (profile.getClasseType() != null) {
                classe.setPrefix(profile.getClasseType().toString());
            }
        }

        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(12);

        Score void1 = obj.getScore("  ");
        void1.setScore(11);

        Score online_string = obj.getScore("§6Joueurs en lignes");
        online_string.setScore(10);
        Team online = board.registerNewTeam("online");
        online.addEntry(ChatColor.BLUE + "" + ChatColor.WHITE);
        online.setPrefix(""+Bukkit.getOnlinePlayers().size());
        obj.getScore(ChatColor.BLUE + "" + ChatColor.WHITE).setScore(9);

        Score void2 = obj.getScore(" ");
        void2.setScore(8);

        Score money_string = obj.getScore("§6Argent");
        money_string.setScore(7);
        Team money = board.registerNewTeam("money");
        money.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        money.setPrefix(""+((int)Main.economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()))));
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(6);

        player.setScoreboard(board);
    }

    public static void updatePlayerScoreboard(Player p, int onlinePlayers) {
        Scoreboard board = p.getScoreboard();
        Team online = board.getTeam("online");
        online.setPrefix(""+onlinePlayers);
        Team money = board.registerNewTeam("money");
        money.setPrefix(""+((int)Main.economy.getBalance(Bukkit.getOfflinePlayer(p.getUniqueId()))));
    }

    public static void removeScoreBoard(Player player) {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

}

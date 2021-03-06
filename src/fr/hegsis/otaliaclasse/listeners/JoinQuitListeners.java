package fr.hegsis.otaliaclasse.listeners;

import fr.hegsis.otaliaclasse.Main;
import fr.hegsis.otaliaclasse.profiles.Profile;
import fr.hegsis.otaliaclasse.utils.ScoreBoardManager;
import fr.hegsis.otaliaclasse.utils.file.json.JsonFileUtils;
import fr.hegsis.otaliaclasse.utils.file.json.ProfileSerializationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class JoinQuitListeners implements Listener {

    private File saveDir;
    private Main main;

    public JoinQuitListeners(Main main) {
        this.main = main;
        this.saveDir = new File(main.getDataFolder(), "/profiles/");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        File file = new File(saveDir, p.getName() + ".json");

        if (file.exists()) {
            ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();
            String json = JsonFileUtils.loadJson(file);
            Profile profile = profileSerializationManager.deserialize(json);
            main.playersProfile.put(p.getName(), profile);
            return;
        }

        main.sendMessage("on-join-no-classe", p, "");
        ScoreBoardManager.setScoreBoard(p);
        int onlinePlayer = Bukkit.getOnlinePlayers().size();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (p != player) {
                ScoreBoardManager.updatePlayerScoreboard(p, onlinePlayer);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (main.playersProfile.containsKey(p.getName())) {
            main.saveProfileOnJson(p, main.playersProfile.get(p.getName()));
            main.playersProfile.remove(p.getName());
        }

        int onlinePlayer = Bukkit.getOnlinePlayers().size();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.updatePlayerScoreboard(p, onlinePlayer);
        }
    }
}

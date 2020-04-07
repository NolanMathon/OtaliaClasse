package fr.hegsis.otaliaclasse.utils.file.yaml;

import fr.hegsis.otaliaclasse.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlFileUtils {

    public static void createFile(YamlFiles filename) {
        File file = getFile(filename);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(YamlFiles filename) {
        return new File( Main.getInstance().getDataFolder() + File.separator + filename.toString().toLowerCase()+".yml");
    }

    public static FileConfiguration getFileConfiguration(YamlFiles filename) {
        return YamlConfiguration.loadConfiguration(getFile(filename));
    }

    public static void saveFile(FileConfiguration fileConfiguration, YamlFiles filename) {
        try {
            fileConfiguration.save(getFile(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

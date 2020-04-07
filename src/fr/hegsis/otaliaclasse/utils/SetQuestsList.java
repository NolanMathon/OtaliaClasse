package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.quests.Quest;
import fr.hegsis.otaliaclasse.quests.QuestAction;
import fr.hegsis.otaliaclasse.quests.QuestType;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFileUtils;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class SetQuestsList {

    public static List<Quest> setTitanQuests() {
        List<Quest> questList = new ArrayList<>();

        FileConfiguration tqfc = YamlFileUtils.getFileConfiguration(YamlFiles.TITAN_QUESTS); //tqfc → titanQuestsFileConfiguration
        ConfigurationSection cs = tqfc.getConfigurationSection("quests");
        if (cs!=null) {
            QuestType questType;
            QuestAction questAction;
            int amount;
            int xpReward;

            Material material;
            EntityType entityType;
            short data;

            for (String s : cs.getKeys(false)) {
                questType = QuestType.valueOf(cs.getString(s+".type"));
                questAction = QuestAction.valueOf(cs.getString(s+".action"));
                amount = cs.getInt(s+".amount");
                xpReward = cs.getInt(s+".xp-reward");
                System.out.println(s + " - " + questType.toString() + " - " + questAction.toString() + " - " + amount + " - " + xpReward);

                if (questAction == QuestAction.TUER || questAction == QuestAction.TUER_A_LA_SUITE) {
                    entityType = EntityType.valueOf(cs.getString(s+".entity"));
                    questList.add(new Quest(Utils.isNumber(s), questType, questAction, entityType, xpReward));
                    break;
                }

                if (questAction == QuestAction.CASSER || questAction == QuestAction.POSER || questAction == QuestAction.RECOLTER) {

                }

                switch (questAction) {
                    case TUER:

                        break;
                    case POSER:

                        break;
                    case CASSER:
                }
            }
        }

        return questList;
    }
}

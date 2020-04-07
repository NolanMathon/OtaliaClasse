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

    public static List<Quest> setQuestList(YamlFiles yamlFiles) {
        List<Quest> questList = new ArrayList<>();

        FileConfiguration qfc = YamlFileUtils.getFileConfiguration(yamlFiles); //pqfc → pirateQuestsFileConfiguration
        ConfigurationSection cs = qfc.getConfigurationSection("quests");
        if (cs!=null) {
            int questId;
            QuestType questType;
            QuestAction questAction;
            int amount;
            int xpReward;

            Material material;
            EntityType entityType;
            short data;

            for (String s : cs.getKeys(false)) {
                questId = Utils.isNumber(s);
                questType = QuestType.valueOf(cs.getString(s+".type"));
                questAction = QuestAction.valueOf(cs.getString(s+".action"));
                amount = cs.getInt(s+".amount");
                xpReward = cs.getInt(s+".xp-reward");
                //System.out.println(s + " - " + questType.toString() + " - " + questAction.toString() + " - " + amount + " - " + xpReward);

                // Si la quête c'est tuer ou tuer plusieurs personnes sans mourrir
                if (questAction == QuestAction.TUER || questAction == QuestAction.TUER_A_LA_SUITE) {
                    entityType = EntityType.valueOf(cs.getString(s+".entity"));
                    questList.add(new Quest(questId, questType, questAction, entityType, xpReward));
                }

                // Si la quête c'est casser, recolter, fabriquer, manger ou pecher
                if (questAction == QuestAction.CASSER || questAction == QuestAction.RECOLTER || questAction == QuestAction.FABRIQUER || questAction == QuestAction.MANGER || questAction == QuestAction.PECHER) {
                    material = Material.valueOf(cs.getString(s+".material"));
                    data = (short) cs.getInt(s+"data");
                    questList.add(new Quest(questId, questType, questAction, amount, material, data, xpReward));
                }

                // Si la quête c'est juste de poser
                if (questAction == QuestAction.POSER) {
                    questList.add(new Quest(questId, questType, questAction, amount, xpReward));
                }
            }
        }

        return questList;
    }
}

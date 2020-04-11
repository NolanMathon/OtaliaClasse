package fr.hegsis.otaliaclasse.utils;

import fr.hegsis.otaliaclasse.rewards.Reward;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFileUtils;
import fr.hegsis.otaliaclasse.utils.file.yaml.YamlFiles;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetRewardsList {

    public static Map<Integer, Reward> setRewardsList(YamlFiles yamlFiles) {
        Map<Integer, Reward> rewardsMap = new HashMap<>();

        FileConfiguration rfc = YamlFileUtils.getFileConfiguration(yamlFiles); //rfc → rewardsFileConfiguration
        ConfigurationSection cs = rfc.getConfigurationSection("rewards");
        if (cs != null) {

            ItemStack it;
            ItemMeta im;

            String enchants;
            String name;
            List<String> lore;

            for (int i=1; i<7; i++) {
                Reward reward = new Reward(i);
                ConfigurationSection levelCs = cs.getConfigurationSection("level"+i);

                for (String s : levelCs.getKeys(false)) {
                    it = new ItemStack(Material.getMaterial(levelCs.getString(s+".item")), levelCs.getInt(s+".amount"), (short) levelCs.getInt(s+".data"));

                    enchants = levelCs.getString(s+".enchants");
                    name = levelCs.getString(s+".name");
                    lore = levelCs.getStringList(s+".description");

                    // Si il y a un/des enchants, un nom ou une description à l'item
                    if (enchants != null || name != null || lore.size() > 0) {
                        im = it.getItemMeta();

                        // Si il y a des enchants
                        if (enchants != null) im = convertStringEnchant(im, enchants);

                        // Si il y a un nom
                        if (name != null) im.setDisplayName(name.replaceAll("&", "§"));

                        // Si il y a une description
                        if (lore.size() > 0) im.setLore(StringUtils.convertLoreColorCode(lore));
                        it.setItemMeta(im);
                    }
                    reward.addRewardsItem(it);
                }
                rewardsMap.put(i, reward);
            }
        }

        return rewardsMap;
    }

    private static ItemMeta convertStringEnchant(ItemMeta im, String enchants) {
        String[] enchant;
        if (enchants.contains(";")) {
            enchant = enchants.split(";");
        } else {
            enchant = new String[1];
            enchant[0] = enchants;
        }


        for (String s : enchant) {
            String[] enchantLevel;
            int level = 1;
            if (s.contains(":")) {
                enchantLevel = s.split(":");
                level = Utils.isNumber(enchantLevel[1]);
            } else {
                enchantLevel = new String[1];
                enchantLevel[0] = s;
            }

            im.addEnchant(Enchantment.getByName(enchantLevel[0]), level, true);
        }

        return im;
    }
}

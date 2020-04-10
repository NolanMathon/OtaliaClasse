package fr.hegsis.otaliaclasse.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Reward {
    private int level;
    private List<ItemStack> rewardsItem;

    public Reward(int level, List<ItemStack> rewardsItem) {
        this.level = level;
        this.rewardsItem = rewardsItem;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<ItemStack> getRewardsItem() {
        return rewardsItem;
    }

    public void setRewardsItem(List<ItemStack> rewardsItem) {
        this.rewardsItem = rewardsItem;
    }

    public void give(Player p) {

    }
}

package fr.hegsis.otaliaclasse.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Reward {
    private int level;
    private List<ItemStack> rewardsItem;

    public Reward(int level, List<ItemStack> rewardsItem) {
        this.level = level;
        this.rewardsItem = rewardsItem;
    }

    public Reward(int level) {
        this.level = level;
        this.rewardsItem = new ArrayList<>();
    }

    public void addRewardsItem(ItemStack itemStack) {
        this.rewardsItem.add(itemStack);
    }

    public void give(Player p) {
        for (ItemStack it : rewardsItem) {
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(it);
            } else {
                p.getLocation().getWorld().dropItemNaturally(p.getLocation(), it);
            }
        }
    }
}

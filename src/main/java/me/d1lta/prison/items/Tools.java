package me.d1lta.prison.items;

import me.d1lta.prison.commands.Upgrade;
import org.bukkit.inventory.ItemStack;

public class Tools {

    public static ItemStack pickaxe() {
        return Upgrade.getUpgradedTool(1, "pickaxe");
    }

    public static ItemStack shovel() {
        return Upgrade.getUpgradedTool(1, "shovel");
    }

    public static ItemStack axe() {
        return Upgrade.getUpgradedTool(1, "axe");
    }

    public static ItemStack shears() {
        return Upgrade.getUpgradedTool(1, "shears");
    }
}

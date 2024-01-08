package me.d1lta.prison.items;

import me.d1lta.prison.commands.Upgrade;
import org.bukkit.inventory.ItemStack;

public class Tool {

    public static ItemStack pickaxe() { return Upgrade.getPrisonItem(null, "pickaxe", 1, false); }

    public static ItemStack rare_pickaxe() { return Upgrade.getPrisonItem(null, "rare_pickaxe", 1, false); }

    public static ItemStack shovel() {
        return Upgrade.getPrisonItem(null, "shovel", 1, false);
    }

    public static ItemStack axe() {
        return Upgrade.getPrisonItem(null, "axe", 1, false);
    }

    public static ItemStack shears() {
        return Upgrade.getPrisonItem(null, "shears", 1, false);
    }
}

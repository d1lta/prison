package me.d1lta.prison.items;

import me.d1lta.prison.commands.Upgrade;
import org.bukkit.inventory.ItemStack;

public class Armor {

    public static ItemStack helmet() { return Upgrade.getPrisonItem(null, "helmet", 1, false); }

    public static ItemStack chestplate() { return Upgrade.getPrisonItem(null, "chestplate", 1, false); }

    public static ItemStack leggings() { return Upgrade.getPrisonItem(null, "leggings", 1, false); }

    public static ItemStack boots() { return Upgrade.getPrisonItem(null, "boots", 1, false); }

}

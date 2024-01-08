package me.d1lta.prison.items;

import me.d1lta.prison.commands.Upgrade;
import org.bukkit.inventory.ItemStack;

public class Weapon {

    public static ItemStack sword() { return Upgrade.getPrisonItem(null, "sword", 1, false); }

    public static ItemStack bow() { return Upgrade.getPrisonItem(null, "bow", 1, false); }

}

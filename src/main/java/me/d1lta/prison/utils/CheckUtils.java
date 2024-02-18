package me.d1lta.prison.utils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckUtils {

    public static boolean checkForNull(ItemStack stack) {
        return (stack != null && !stack.getType().equals(Material.AIR) && stack.getAmount() != 0);
    }

    public static boolean checkForPlayer(Entity e) {
        return (e instanceof Player);
    }

    public static boolean checkForPlayer(Entity e, Entity e1) {
        return ((e instanceof Player) && (e1 instanceof Player));
    }

}

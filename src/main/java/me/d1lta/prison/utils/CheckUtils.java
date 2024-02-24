package me.d1lta.prison.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckUtils {

    /** Возвращает true если предмет ItemStack не null, Material.Air или его количество больше 0 */
    public static boolean checkForNull(ItemStack stack) {
        return (stack != null && !stack.getType().equals(Material.AIR) && stack.getAmount() != 0);
    }
    public static boolean checkForNull(ItemStack ...stack) {
        for (ItemStack it : Arrays.stream(stack).toList()) {
            if (!checkForNull(it)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForPlayer(Entity e) {
        return (e instanceof Player);
    }

    public static boolean checkForPlayer(CommandSender sender) {
        return (sender instanceof Player);
    }

    public static boolean checkForPlayer(Entity e, Entity e1) {
        return ((e instanceof Player) && (e1 instanceof Player));
    }

}

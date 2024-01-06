package me.d1lta.prison.utils;

import de.tr7zw.nbtapi.NBTItem;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class NBT {

    public static ItemStack addNBT(ItemStack stack, String a, Integer b) {
        NBTItem nbtItem = new NBTItem(stack);
        nbtItem.setInteger(a, b);
        return nbtItem.getItem();
    }

    public static ItemStack addNBT(ItemStack stack, String a, String b) {
        NBTItem nbtItem = new NBTItem(stack);
        nbtItem.setString(a, b);
        return nbtItem.getItem();
    }

    public static Integer getIntNBT(ItemStack stack, String a) {
        NBTItem nbtItem = new NBTItem(stack);
        return nbtItem.getInteger(a);
    }

    public static String getStringNBT(ItemStack stack, String a) {
        NBTItem nbtItem = new NBTItem(stack);
        return nbtItem.getString(a);
    }

    public static Set<String> getKeys(ItemStack stack) {
        NBTItem nbtItem = new NBTItem(stack);
        return nbtItem.getKeys();
    }

    public static ItemStack applyNBTs(Map<String, Integer> map, ItemStack stack) {
        for (var i: map.entrySet()) {
            stack = addNBT(stack, i.getKey(), i.getValue());
        }
        return stack;
    }

}

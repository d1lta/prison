package me.d1lta.prison.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

/** Хз куда все эти методы деть, пусть побудут тут */
public class EventMethods {

    public static List<Component> elderEnchantingLore(ItemStack stack) {
        Map<String, Integer> enchants = new HashMap<>();
        List<Component> lore = new ArrayList<>();
        for (String it : NBT.getKeys(stack)) {
            for (Enchantments e: Enchantments.values()) {
                if (e.getName().equals(it)) {
                    enchants.put(e.getName(), NBT.getIntNBT(stack, e.getName()));
                }
            }
        }
        if (!enchants.isEmpty()) {
            lore.add(CValues.get("").create());
            lore.add(CValues.get("Древние зачарования", 176, 0, 190).create());
            enchants.forEach((k,v) -> lore.add(Enchantments.getEnchantment(k).getColoredNameWithLevel(v)));
        }
        lore.add(CValues.get("").create());
        lore.add(DComponent.create(CValues.get("Уровень предмета >> "), CValues.get(String.valueOf(NBT.getIntNBT(stack, "level")), 250, 250, 90)));
        return lore;
    }

    public static int elderDustChance(ItemStack cursor, ItemStack current) {
        return (Integer.parseInt(NBT.getStringNBT(current, "chance")) + Integer.parseInt(NBT.getStringNBT(cursor, "chance")));
    }

    public static boolean elderDustCheck(ItemStack cursor, ItemStack current) {
        if (!NBT.getKeys(current).contains("chance") || !NBT.getKeys(cursor).contains("chance")) { return true; } // TODO: int check
        if (Integer.parseInt(NBT.getStringNBT(current, "chance")) == 100) { return true; }
        if (Integer.parseInt(NBT.getStringNBT(cursor, "chance")) > 100) { return true; }
        return cursor.getAmount() > 1 || current.getAmount() > 1;
    }

}

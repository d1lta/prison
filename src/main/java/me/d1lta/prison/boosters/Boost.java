package me.d1lta.prison.boosters;

import java.util.List;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Boost {

    boolean local = false;
    String type;
    double multiplier;
    int amount;

    public Boost(Boolean local, String type, double multiplier, int amount) {
        this.local = local;
        this.type = type;
        this.multiplier = multiplier;
        this.amount = amount;
    }

    public Material getMat() {
        if (this.type.equals("blocks")) {
            if (this.local) {
                return Material.GOLD_INGOT;
            } else {
                return Material.GOLD_BLOCK;
            }
        } else if (this.type.equals("money")) {
            if (this.local) {
                return Material.DIAMOND;
            } else {
                return Material.DIAMOND_BLOCK;
            }
        } else {
            return Material.DIRT;
        }
    }

    public static ItemStack setMeta(ItemStack stack, Boost boost) {
        switch (boost.type) {
            case "blocks" -> {
                ItemMeta meta = stack.getItemMeta();
                if (boost.local) {
                    meta.displayName(ComponentUtils.component("Локальный бустер блоков"));
                } else {
                    meta.displayName(ComponentUtils.component("Глобальный бустер блоков"));
                }
                meta.lore(List.of(
                        ComponentUtils.component("Множитель: " + boost.multiplier),
                        ComponentUtils.component("Количество: " + boost.amount)));
                stack.setItemMeta(meta);
            }
            case "money" -> {
                ItemMeta meta = stack.getItemMeta();
                if (boost.local) {
                    meta.displayName(ComponentUtils.component("Локальный бустер денег"));
                } else {
                    meta.displayName(ComponentUtils.component("Глобальный бустер денег"));
                }
                meta.lore(List.of(
                        ComponentUtils.component("Множитель: " + boost.multiplier),
                        ComponentUtils.component("Количество: " + boost.amount)));
                stack.setItemMeta(meta);
            }
        }
        stack = NBT.addNBT(stack, "type", boost.type);
        stack = NBT.addNBT(stack, "amount", boost.amount);
        stack = NBT.addNBT(stack, "local", String.valueOf(boost.local));
        stack = NBT.addNBT(stack, "multiplier", String.valueOf(boost.multiplier));
        return stack;
    }
}

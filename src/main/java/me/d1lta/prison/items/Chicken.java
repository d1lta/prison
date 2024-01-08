package me.d1lta.prison.items;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Chicken {

    public static ItemStack getChicken() {
        ItemStack chicken = new ItemStack(Material.COOKED_CHICKEN);
        ItemMeta meta = chicken.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Жареная курица", TextColor.color(214, 144, 0)));
        chicken.setItemMeta(meta);
        return chicken;
    }

}

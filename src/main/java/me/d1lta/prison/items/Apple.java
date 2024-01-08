package me.d1lta.prison.items;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Apple {

    public static ItemStack getApple() {
        ItemStack apple = new ItemStack(Material.APPLE);
        ItemMeta meta = apple.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Яблоко", TextColor.color(214, 144, 0)));
        apple.setItemMeta(meta);
        return apple;
    }

}

package me.d1lta.prison.items;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Key {

    public ItemStack getKey() {
        ItemStack key = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = key.getItemMeta();
        meta.displayName(ComponentUtils.component("Ключ", TextColor.color(214, 144, 0)));
        key.setItemMeta(meta);
        return key;
    }

}

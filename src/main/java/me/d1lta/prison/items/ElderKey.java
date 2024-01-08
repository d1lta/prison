package me.d1lta.prison.items;

import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderKey {

    public static ItemStack getKey() {
        ItemStack key = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = key.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Древний ключ", TextColor.color(214, 144, 0)));
        key.setItemMeta(meta);
        key = NBT.addNBT(key, "type", "elder_key");
        return key;
    }

}

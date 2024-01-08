package me.d1lta.prison.items;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderStar {

    public static ItemStack getStar() {
        ItemStack key = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = key.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Древняя звезда", TextColor.color(214, 144, 0)));
        key.setItemMeta(meta);
        return key;
    }

}

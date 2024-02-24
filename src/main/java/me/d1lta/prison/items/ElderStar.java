package me.d1lta.prison.items;

import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderStar {

    public static ItemStack getStar() {
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = star.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(DComponent.create("Древняя звезда", TextColor.color(214, 144, 0)));
        star.setItemMeta(meta);
        star = NBT.addNBT(star, "type", "elderstar");
        return star;
    }

}

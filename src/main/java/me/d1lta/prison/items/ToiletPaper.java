package me.d1lta.prison.items;

import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToiletPaper {

    public static ItemStack getPaper() {
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta meta = paper.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(DComponent.create("Туалетка", TextColor.color(214, 144, 0)));
        paper.setItemMeta(meta);
        paper = NBT.addNBT(paper, "toiletpaper", "1");
        return paper;
    }

}

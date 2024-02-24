package me.d1lta.prison.items;

import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminStick {

    public static ItemStack getStick() {
        ItemStack apple = new ItemStack(Material.STICK);
        ItemMeta meta = apple.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(DComponent.create("Убийственная палочка", TextColor.color(214, 144, 0)));
        apple.setItemMeta(meta);
        apple = NBT.addNBT(apple, "adminstick", "1");
        return apple;
    }
}

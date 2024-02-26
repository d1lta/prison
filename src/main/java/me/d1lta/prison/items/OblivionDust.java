package me.d1lta.prison.items;

import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OblivionDust {

    public static ItemStack getOblivionDust() {
        ItemStack stack = new ItemStack(Material.BONE_MEAL);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(CValues.get("Порошок забвения", 255, 200, 100).create());
        meta.lore(DComponent.createList(CValues.get("При использовании вы забываете все свои навыки.")));
        stack.setItemMeta(meta);
        stack = NBT.addNBT(stack, "type", "oblivion");
        return stack;
    }
}

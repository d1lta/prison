package me.d1lta.prison.items;

import java.util.List;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VaultAccess {

    public static ItemStack getAccess() {
        ItemStack access = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = access.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.lore(List.of(CValues.get("Этот предмет открывает доступ в подвал", 0, 220, 200).create()));
        meta.displayName(DComponent.create("Доступ в подвал", TextColor.color(214, 144, 0)));
        access.setItemMeta(meta);
        return access;
    }

}

package me.d1lta.prison.items;

import static me.d1lta.prison.enchants.EnchantmentBook.getColor;

import java.util.List;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderDust {

    public static ItemStack getDust(int chance) {
        ItemStack dust = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = dust.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Древняя пыль", TextColor.color(214, 144, 0)));
        List<Component> lore = List.of(
                ComponentUtils.component("Повышает шанс наложения", TextColor.color(201, 201, 193)),
                ComponentUtils.component("древней книги на ", TextColor.color(201, 201, 193)).append(
                        ComponentUtils.component(String.valueOf(chance), getColor(chance))).append(
                                ComponentUtils.component("%", TextColor.color(201, 201, 193))));
        meta.lore(lore);
        dust.setItemMeta(meta);
        dust = NBT.addNBT(dust, "chance", String.valueOf(chance));
        dust = NBT.addNBT(dust, "type", "elder_dust");
        return dust;
    }
}

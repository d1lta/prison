package me.d1lta.prison.enchants;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.NBT;
import me.d1lta.prison.utils.RomanNumeration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class EnchantmentBook {

    private String enchantment;
    private int lvl;
    private List<Component> description;
    private List<Material> applicableTo;

    public EnchantmentBook(String enchantment, int lvl, List<Component> description, List<Material> applicableTo) {
        this.enchantment = enchantment;
        this.lvl = lvl;
        this.description = description;
        this.applicableTo = applicableTo;
    }

    public ItemStack getBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = book.getItemMeta();
        meta.displayName(ComponentUtils.component("Древняя книга ", TextColor.color(191, 0, 255)));
        List<Component> list = new ArrayList<>();
        list.add(EnchTranslate.translateWithColor(enchantment).append(ComponentUtils.component(" " + RomanNumeration.get(lvl), EnchTranslate.getColor(enchantment))));
        list.add(ComponentUtils.component(""));
        list.addAll(description);
        meta.lore(list);
        book.setItemMeta(meta);
        book = NBT.addNBT(book, "elderbook", "1");
        book = NBT.addNBT(book, this.enchantment, this.lvl);
        return book;
    }

    public static boolean isEnchantingBook(ItemStack stack) {
        if (NBT.getStringNBT(stack, "elderbook").equals("1")) {
            return true;
        }
        return false;
    }
}

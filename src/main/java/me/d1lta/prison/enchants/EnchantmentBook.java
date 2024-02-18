package me.d1lta.prison.enchants;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class EnchantmentBook {

    private final Enchantments enchantment;
    private final int lvl;
    private final List<Component> description;
    public List<Material> applicableTo;
    public int chance;

    public EnchantmentBook(Enchantments enchantment, int lvl, int chance, List<Component> description, List<Material> applicableTo) {
        this.enchantment = enchantment;
        this.lvl = lvl;
        this.description = description;
        this.applicableTo = applicableTo;
        this.chance = chance;
    }

    public ItemStack getBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = book.getItemMeta();
        meta.displayName(ComponentUtils.component("Древняя книга ", TextColor.color(191, 0, 255)));
        List<Component> list = new ArrayList<>();
        list.add(enchantment.getColoredNameWithLevel(lvl));
        list.add(ComponentUtils.component(""));
        list.addAll(description);
        list.add(ComponentUtils.component(""));
        list.add(ComponentUtils.component("Шанс наложения ", TextColor.color(255, 255, 0)).append(
                ComponentUtils.component(String.valueOf(this.chance), getColor(chance)).append(
                        ComponentUtils.component("%", TextColor.color(255, 255, 0)))));
        meta.lore(list);
        book.setItemMeta(meta);
        book = NBT.addNBT(book, "elderbook", "1");
        book = NBT.addNBT(book, "chance", String.valueOf(this.chance));
        book = NBT.addNBT(book, "type", this.enchantment.getName());
        book = NBT.addNBT(book, this.enchantment.getName(), this.lvl);
        return book;
    }

    public static boolean isEnchantingBook(ItemStack stack) {
        if (NBT.getStringNBT(stack, "elderbook").equals("1")) {
            return true;
        }
        return false;
    }

    public static ItemStack replaceChanceLore(ItemStack stack, int chance) {
        List<Component> lore = stack.getItemMeta().lore();
        assert lore != null;
        lore.set(lore.size() - 1, ComponentUtils.component("Шанс наложения ", TextColor.color(255, 255, 0)).append(
                        ComponentUtils.component(String.valueOf(chance), getColor(chance)).append(
                        ComponentUtils.component("%", TextColor.color(255, 255, 0)))));
        ItemMeta meta = stack.getItemMeta();
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    public static TextColor getColor(int chance) {
        if (chance == 0) {
            return TextColor.color(255, 0, 0);
        } else if (chance > 0 && chance <= 25) {
            return TextColor.color(255, 64, 0);
        } else if (chance > 25 && chance <= 50) {
            return TextColor.color(255, 162, 0);
        } else if (chance > 50 && chance <= 75) {
            return TextColor.color(188, 255, 0);
        } else if (chance > 75) {
            return TextColor.color(0, 255, 0);
        }
        return TextColor.color(252, 0, 255);
    }

}

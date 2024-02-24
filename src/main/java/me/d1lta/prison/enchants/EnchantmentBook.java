package me.d1lta.prison.enchants;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class EnchantmentBook {

    private final Enchantments enchantment;
    private final int lvl;
    private final List<CValues> description;
    public List<Material> applicableTo;
    public int chance;

    public EnchantmentBook(Enchantments enchantment, int lvl, int chance, List<CValues> description, List<Material> applicableTo) {
        this.enchantment = enchantment;
        this.lvl = lvl;
        this.description = description;
        this.applicableTo = applicableTo;
        this.chance = chance;
    }

    public ItemStack getBook() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = book.getItemMeta();
        meta.displayName(CValues.get("Древняя книга", 191, 0, 255).create());
        List<Component> list = new ArrayList<>();
        list.add(enchantment.getColoredNameWithLevel(lvl));
        list.add(DComponent.create(""));
        description.forEach(it -> list.add(it.create()));
        list.add(DComponent.create(""));
        list.add(getChance(chance));
        meta.lore(list);
        book.setItemMeta(meta);
        book = NBT.addNBT(book, "elderbook", "1");
        book = NBT.addNBT(book, "chance", String.valueOf(this.chance));
        book = NBT.addNBT(book, "type", this.enchantment.getName());
        book = NBT.addNBT(book, this.enchantment.getName(), this.lvl);
        return book;
    }

    public static boolean isEnchantingBook(ItemStack stack) {
        return NBT.getStringNBT(stack, "elderbook").equals("1");
    }

    public static ItemStack replaceChanceLore(ItemStack stack, int chance) {
        List<net.kyori.adventure.text.Component> lore = stack.getItemMeta().lore();
        assert lore != null;
        lore.set(lore.size() -1, getChance(chance));
        ItemMeta meta = stack.getItemMeta();
        meta.lore(lore);
        stack.setItemMeta(meta);
        return stack;
    }

    private static Component getChance(int chance) {
        return DComponent.create(
                CValues.get("Шанс наложения ", 255, 255, 0),
                CValues.get(String.valueOf(chance), getColor(chance)),
                CValues.get("%", 255, 255, 0)
        );
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

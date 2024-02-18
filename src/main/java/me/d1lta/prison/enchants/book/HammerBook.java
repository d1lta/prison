package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class HammerBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.IRON_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.SHEARS
    );

    public HammerBook(int lvl, int chance) {
        super(Enchantments.HAMMER, lvl, chance, List.of(
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                ComponentUtils.component("Крутая чарка которая копает за тебя", TextColor.color(255, 196, 129)),
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }
}

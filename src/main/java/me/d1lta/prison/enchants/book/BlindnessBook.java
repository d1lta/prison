package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import org.bukkit.Material;

public class BlindnessBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.DIAMOND_SWORD,
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE
    );

    public BlindnessBook(int lvl, int chance) {
        super(Enchantments.BLINDNESS, lvl, chance, List.of(
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 122),
                CValues.get("Тупая чарка которая делает", 129, 129, 122),
                CValues.get("твоего врага слепым", 129, 129, 122),
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 122)
        ), applicableTo);
    }

}

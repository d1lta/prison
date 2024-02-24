package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import org.bukkit.Material;

public class StrenghteningBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.LEATHER_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE
    );

    public StrenghteningBook(int lvl, int chance) {
        super(Enchantments.STRENGHTENING, lvl, chance, List.of(
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129),
                CValues.get("Тупая чарка которая даёт тебе", 255, 196, 129),
                CValues.get("стальную кожу", 255, 196, 129),
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129)
        ), applicableTo);
    }

}

package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import org.bukkit.Material;

public class NinjaBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.LEATHER_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.IRON_BOOTS,
            Material.DIAMOND_BOOTS
    );

    public NinjaBook(int lvl, int chance) {
        super(Enchantments.NINJA, lvl, chance, List.of(
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129),
                CValues.get("Тупая чарка которая делает", 255, 196, 129),
                CValues.get("из тебя Наруто Узумаки", 255, 196, 129),
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129)
        ), applicableTo);
    }

}

package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import org.bukkit.Material;

public class BoxerBook extends EnchantmentBook {

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

    public BoxerBook(int lvl, int chance) {
        super(Enchantments.BOXER, lvl, chance, List.of(
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129),
                CValues.get("Тупая чарка которая делает", 255, 196, 129),
                CValues.get("твоего врага попрыгунчиком", 255, 196, 129),
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 129)
        ), applicableTo);
    }

}

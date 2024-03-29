package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import org.bukkit.Material;

public class ArchaeologyBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.IRON_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE,
            Material.SHEARS
    );

    public ArchaeologyBook(int lvl, int chance) {
        super(Enchantments.ARCHAEOLOGY, lvl, chance, List.of(
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 122),
                CValues.get("Крутая чарка которая даёт спешку", 255, 196, 129),
                CValues.get("- - - - - - - - - - - - - - - - -", 129, 129, 122)),
                applicableTo);
    }
}

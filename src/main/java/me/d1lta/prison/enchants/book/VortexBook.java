package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class VortexBook extends EnchantmentBook {

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

    public VortexBook(int lvl, int chance) {
        super(Enchantments.VORTEX, lvl, chance, List.of(
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                DComponent.create("Тупая чарка которая делает", TextColor.color(255, 196, 129)),
                DComponent.create("твоих врагов придурками", TextColor.color(255, 196, 129)),
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

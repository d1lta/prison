package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.ComponentUtils;
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
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                ComponentUtils.component("Тупая чарка которая делает", TextColor.color(255, 196, 129)),
                ComponentUtils.component("твоих врагов придурками", TextColor.color(255, 196, 129)),
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

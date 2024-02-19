package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class FieryBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.LEATHER_LEGGINGS,
            Material.CHAINMAIL_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.DIAMOND_LEGGINGS
    );

    public FieryBook(int lvl, int chance) {
        super(Enchantments.FIERY, lvl, chance, List.of(
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                DComponent.create("Тупая чарка которая делает тебя hot baby", TextColor.color(255, 196, 129)),
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public class CatGraceBook extends EnchantmentBook {

    public static List<Material> applicableTo = List.of(
            Material.LEATHER_BOOTS,
            Material.CHAINMAIL_BOOTS,
            Material.IRON_BOOTS,
            Material.DIAMOND_BOOTS
    );

    public CatGraceBook(int lvl, int chance) {
        super(Enchantments.CATGRACE, lvl, chance, List.of(
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                DComponent.create("Крутая чарка которая делает", TextColor.color(255, 196, 129)),
                DComponent.create("тебя как котик, прыгай сколько хочешь!", TextColor.color(255, 196, 129)),
                DComponent.create("Но от смерти тебя это не спасёт...", TextColor.color(255, 196, 129)),
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

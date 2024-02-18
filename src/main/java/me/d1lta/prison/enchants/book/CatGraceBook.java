package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.ComponentUtils;
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
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                ComponentUtils.component("Крутая чарка которая делает", TextColor.color(255, 196, 129)),
                ComponentUtils.component("тебя как котик, прыгай сколько хочешь!", TextColor.color(255, 196, 129)),
                ComponentUtils.component("Но от смерти тебя это не спасёт...", TextColor.color(255, 196, 129)),
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
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
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                DComponent.create("Тупая чарка которая делает", TextColor.color(255, 196, 129)),
                DComponent.create("тебя похожим на Наруто Узумаки!", TextColor.color(255, 196, 129)),
                DComponent.create("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

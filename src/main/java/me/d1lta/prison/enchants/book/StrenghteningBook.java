package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.format.TextColor;
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
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122)),
                ComponentUtils.component("Зачарование которые делает", TextColor.color(255, 196, 129)),
                ComponentUtils.component("из тебя лампу. Будешь", TextColor.color(255, 196, 129)),
                ComponentUtils.component("светиться от счаться в PVP!", TextColor.color(255, 196, 129)),
                ComponentUtils.component("- - - - - - - - - - - - - - - - -", TextColor.color(129, 129, 122))
        ), applicableTo);
    }

}

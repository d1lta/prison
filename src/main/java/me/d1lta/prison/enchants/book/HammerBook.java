package me.d1lta.prison.enchants.book;

import java.util.List;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enchants.enchantments.Hammer;
import me.d1lta.prison.utils.ComponentUtils;

public class HammerBook extends EnchantmentBook {

    public HammerBook(int lvl) {
        super("hammer", lvl, List.of(
                ComponentUtils.component("line_1"),
                ComponentUtils.component("line_2")
        ), Hammer.applicableTo);
    }
}

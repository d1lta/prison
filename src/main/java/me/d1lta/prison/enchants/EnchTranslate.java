package me.d1lta.prison.enchants;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class EnchTranslate {

    public static Component translateWithColor(String eng) {
        switch (eng.toLowerCase()) {
            case "hammer" -> { return ComponentUtils.component("Молот", TextColor.color(255, 172, 0)); }
            default -> { return ComponentUtils.component("null", TextColor.color(0, 214, 199)); }
        }
    }

    public static TextColor getColor(String eng) {
        switch (eng.toLowerCase()) {
            case "hammer" -> { return TextColor.color(255, 172, 0); }
            default -> { return TextColor.color(0, 214, 199); }
        }
    }
}

package me.d1lta.prison.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ComponentUtils {

    public static Component component(String text, TextColor color) {
        return Component.text(text).color(color).decoration(TextDecoration.ITALIC, false);
    }

    public static Component component(String text) {
        return Component.text(text).color(TextColor.color(255,255,255)).decoration(TextDecoration.ITALIC, false);
    }
}


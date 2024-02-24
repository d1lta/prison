package me.d1lta.prison.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Range;

public class DComponent {

    public static Component create(String text, TextColor color) {
        return Component.text(text).color(color).decoration(TextDecoration.ITALIC, false);
    }

    public static Component create(String text) {
        return Component.text(text).color(TextColor.color(255, 255, 255))
                .decoration(TextDecoration.ITALIC, false);
    }

    public static Component create(CValues ...values) {
        Component component = Component.text("");
        for (CValues val: values) {
            component = component.append(create(val.text, val.color));
        }
        return component;
    }

    public static Component create(CValues value) {
        return create(value.text, value.color);
    }

    public static class CValues {

        String text;
        TextColor color;

        public CValues(String text, TextColor color) {
            this.text = text;
            this.color = color;
        }

        public static CValues get(String text, TextColor color) {
            return new CValues(text, color);
        }

        public static CValues get(String text,  @Range(from = 0L, to = 255L) int r,  @Range(from = 0L, to = 255L) int g,  @Range(from = 0L, to = 255L) int b) {
            return new CValues(text, TextColor.color(r, g, b));
        }

        public static CValues get(String text) {
            return new CValues(text, TextColor.color(255, 255, 255));
        }

        public static CValues get(Component component, TextColor color) {
            return new CValues(PlainTextComponentSerializer.plainText().serialize(component), color);
        }

        public static CValues get(Component component, @Range(from = 0L, to = 255L) int r,  @Range(from = 0L, to = 255L) int g,  @Range(from = 0L, to = 255L) int b) {
            return new CValues(PlainTextComponentSerializer.plainText().serialize(component), TextColor.color(r, g, b));
        }

        public Component create() {
            return DComponent.create(this);
        }
    }
}
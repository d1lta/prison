package me.d1lta.prison.enums;

import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;

public enum Factions {

    ASIAN("Азиаты", TextColor.color(255, 229, 0), Material.GOLD_INGOT, "asian"),
    LATINOS("Латиносы", TextColor.color(153, 90, 24), Material.COPPER_INGOT, "latin"),
    NIGGERS("Ниггеры", TextColor.color(75, 77, 79), Material.NETHERITE_INGOT, "nigger"),
    WHITE("Белые", TextColor.color(224, 223, 219), Material.IRON_INGOT, "white"),
    NO_FACTION("Без фракции", TextColor.color(255, 255, 255), Material.BEDROCK, "no_faction");

    private final String name;
    private final TextColor color;
    private final Material material;
    private final String configname;

    Factions(String name, TextColor color, Material material, String configname) {
        this.name = name;
        this.color = color;
        this.material = material;
        this.configname = configname;
    }

    public String getName() { return name; }

    public TextColor getColor() { return color; }

    public Material getMaterial() { return material; }

    public String getConfigName() { return configname; }

    public Component getComponent() { return ComponentUtils.component(name, color); }

    public static Factions getFaction(String name) {
        for (Factions f: Factions.values()) {
            if (f.name.equals(name)) {
                return f;
            }
        }
        return null;
    }


}

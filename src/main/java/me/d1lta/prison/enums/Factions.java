package me.d1lta.prison.enums;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum Factions {

    ASIAN("Азиаты", TextColor.color(255, 229, 0), Material.GOLD_INGOT, "asian"),
    LATINOS("Латиносы", TextColor.color(153, 90, 24), Material.COPPER_INGOT, "latin"),
    NIGGERS("Ниггеры", TextColor.color(75, 77, 79), Material.NETHERITE_INGOT, "nigger"),
    WHITE("Белые", TextColor.color(224, 223, 219), Material.IRON_INGOT, "white"),
    NO_FACTION("Без фракции", TextColor.color(255, 255, 255), Material.BEDROCK, "no_faction");

    private final String name;
    private final TextColor color;
    private final Material material;
    private final String configName;

    Factions(String name, TextColor color, Material material, String configname) {
        this.name = name;
        this.color = color;
        this.material = material;
        this.configName = configname;
    }

    public String getName() { return name; }

    public TextColor getColor() { return color; }

    public Material getMaterial() { return material; }

    public String getConfigName() { return configName; }

    public Component getComponent() { return DComponent.create(name, color); }

    public Material getWarzoneBlockMat() {
        return switch (this) {
            case WHITE -> Material.LIGHT_GRAY_WOOL;
            case ASIAN -> Material.YELLOW_WOOL;
            case LATINOS -> Material.ORANGE_WOOL;
            case NIGGERS -> Material.BLACK_WOOL;
            case NO_FACTION -> Material.WHITE_WOOL;
        };
    }

    public Material getWarzoneGlassMat() {
        return switch (this) {
            case WHITE -> Material.GRAY_STAINED_GLASS;
            case ASIAN -> Material.YELLOW_STAINED_GLASS;
            case LATINOS -> Material.ORANGE_STAINED_GLASS;
            case NIGGERS -> Material.BLACK_STAINED_GLASS;
            case NO_FACTION -> Material.WHITE_STAINED_GLASS;
        };
    }

    public static Factions getFaction(String name) {
        for (Factions f: Factions.values()) {
            if (f.name.equals(name)) {
                return f;
            }
        }
        return NO_FACTION;
    }

    public static List<LittlePlayer> getPlayersInFaction(Factions faction) {
        List<LittlePlayer> list = new ArrayList<>();
        for (Player pl: Bukkit.getOnlinePlayers()) {
            if (new LittlePlayer(pl.getUniqueId()).getFaction().equals(faction)) {
                list.add(new LittlePlayer(pl.getUniqueId()));
            }
        }
        return list;
    }

    public static boolean isPlayersInSingleFaction(LittlePlayer pl1, LittlePlayer pl2) {
        return pl1.getFaction().equals(pl2.getFaction());
    }

    public static boolean isPlayersInSingleFaction(List<LittlePlayer> list) {
        if (list.isEmpty()) { return false; }
        Factions faction = list.get(0).getFaction();
        for (LittlePlayer pl: list) {
            if (!pl.getFaction().equals(faction)) {
                return false;
            }
        }
        return true;
    }
}

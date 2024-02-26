package me.d1lta.prison.utils;

import org.bukkit.Material;

public class Translate {

    public static String upgrade(String s) {
        return switch (s.toLowerCase()) {
            case "money" -> "Денег";
            case "sand" -> "Песок";
            case "dirt" -> "Земля";
            case "gravel" -> "Гравий";
            case "stone" -> "Камень";
            case "coal_ore" -> "Уголь";
            case "iron_ore" -> "Железо";
            case "gold_ore" -> "Золото";
            case "white_wool" -> "Шерсть";
            case "cobweb" -> "Паутина";
            case "oak_wood" -> "Дерево";
            case "rats" -> "Крыс";
            default -> s;
        };
    }

    public static String block(Material material) {
        switch (material) {
            case OAK_WOOD -> { return "Дерево"; }
            case DIRT -> { return  "Грязь"; }
            case GRAVEL -> { return  "Гравий"; }
            case SAND -> { return  "Песок"; }
            case STONE -> { return  "Камень"; }
            case COAL_ORE -> { return  "Уголь"; }
            case IRON_ORE -> { return  "Железо"; }
            case GOLD_ORE -> { return  "Золото"; }
            case WHITE_CONCRETE_POWDER -> { return  "Белый цемент"; }
            case LIGHT_GRAY_CONCRETE_POWDER -> { return  "Серый цемент"; }
            case NETHER_QUARTZ_ORE -> { return  "Кварц"; }
            case SOUL_SAND -> { return  "Песок душ"; }
            case NETHERRACK -> { return  "Адский камень"; }
            case SANDSTONE -> { return  "Песчаник"; }
            case RED_SANDSTONE -> { return  "Красный песчаник"; }
            case QUARTZ_BLOCK -> { return  "Блок кварца"; }
            case PRISMARINE_BRICKS -> { return  "Призмариновый кирпичи"; }
            case PRISMARINE -> { return  "Призмарин"; }
            case PURPUR_BLOCK -> { return  "Пурпур"; }
            case PURPUR_PILLAR -> { return  "Резной пурпур"; }
            case END_STONE_BRICKS -> { return  "Кирпич края"; }
            case WHITE_WOOL -> { return  "Шерсть"; }
            case COBWEB -> { return  "Паутина"; }
            case TERRACOTTA -> { return  "Терракотта"; }
            case BROWN_GLAZED_TERRACOTTA -> { return  "Керамика"; }
            case ICE -> { return  "Лёд"; }
            case PACKED_ICE -> { return  "Плотный лёд"; }
            case PURPLE_TERRACOTTA -> { return  "Фиолетовая терракотта"; }
            case OBSIDIAN -> { return  "Обсидиан"; }
            case LAPIS_BLOCK -> { return  "Блок лазурита"; }
            case IRON_BLOCK -> { return  "Железный блок"; }
            case GOLD_BLOCK -> { return  "Золотой блок"; }
            case DIAMOND_BLOCK -> { return  "Алмазный блок"; }
            default -> { return "null"; }
        }
    }

}

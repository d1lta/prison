package me.d1lta.prison.mines;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AllowedBlocks {

    static TextColor gold = TextColor.color(255,215,0);

    public static List<ItemStack> blocks = Arrays.asList(
            dirt(), gravel(), sand(),
            stone(), coal_ore(), iron_ore(), gold_ore(),
            white_concrete_block(), light_gray_concrete_powder(),
            nether_quartz_ore(), soul_sand(), netherrack(),
            sandstone(), red_sandstone(),
            quartz_block(), prismarine_bricks(), prismarine(),
            purpur_block(), purpur_pillar(), end_stone_bricks(),
            white_wool(), cobweb(),
            terracotta(), brown_glazed_terracotta(),
            ice(), packed_ice(),
            purple_terracotta(), obsidian(),
            lapis_block(), iron_block(), gold_block(), diamond_block());

    public static ItemStack dirt() {
        ItemStack dirt =  new ItemStack(Material.DIRT);
        ItemMeta meta = dirt.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Земля").color(TextColor.color(gold)));
        dirt.setItemMeta(meta);
        return dirt;
    }

    public static ItemStack gravel() {
        ItemStack gravel =  new ItemStack(Material.GRAVEL);
        ItemMeta meta = gravel.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Гравий").color(TextColor.color(gold)));
        gravel.setItemMeta(meta);
        return gravel;
    }

    public static ItemStack sand() {
        ItemStack sand =  new ItemStack(Material.SAND);
        ItemMeta meta = sand.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Песок").color(TextColor.color(gold)));
        sand.setItemMeta(meta);
        return sand;
    }

    public static ItemStack stone() {
        ItemStack stone =  new ItemStack(Material.STONE);
        ItemMeta meta = stone.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Камень").color(TextColor.color(gold)));
        stone.setItemMeta(meta);
        return stone;
    }

    public static ItemStack coal_ore() {
        ItemStack coal_ore =  new ItemStack(Material.COAL_ORE);
        ItemMeta meta = coal_ore.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Уголь").color(TextColor.color(gold)));
        coal_ore.setItemMeta(meta);
        return coal_ore;
    }

    public static ItemStack iron_ore() {
        ItemStack iron_ore =  new ItemStack(Material.IRON_ORE);
        ItemMeta meta = iron_ore.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Железо").color(TextColor.color(gold)));
        iron_ore.setItemMeta(meta);
        return iron_ore;
    }

    public static ItemStack gold_ore() {
        ItemStack gold_ore =  new ItemStack(Material.GOLD_ORE);
        ItemMeta meta = gold_ore.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Золото").color(TextColor.color(gold)));
        gold_ore.setItemMeta(meta);
        return gold_ore;
    }

    public static ItemStack light_gray_concrete_powder() {
        ItemStack light_gray_concrete_powder =  new ItemStack(Material.LIGHT_GRAY_CONCRETE_POWDER);
        ItemMeta meta = light_gray_concrete_powder.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Серый цемент").color(TextColor.color(gold)));
        light_gray_concrete_powder.setItemMeta(meta);
        return light_gray_concrete_powder;
    }

    public static ItemStack white_concrete_block() {
        ItemStack white_concrete_block =  new ItemStack(Material.WHITE_CONCRETE_POWDER);
        ItemMeta meta = white_concrete_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Белый цемент").color(TextColor.color(gold)));
        white_concrete_block.setItemMeta(meta);
        return white_concrete_block;
    }

    public static ItemStack netherrack() {
        ItemStack netherrack =  new ItemStack(Material.NETHERRACK);
        ItemMeta meta = netherrack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Адский камень").color(TextColor.color(gold)));
        netherrack.setItemMeta(meta);
        return netherrack;
    }

    public static ItemStack soul_sand() {
        ItemStack soul_sand =  new ItemStack(Material.SOUL_SAND);
        ItemMeta meta = soul_sand.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Песок душ").color(TextColor.color(gold)));
        soul_sand.setItemMeta(meta);
        return soul_sand;
    }

    public static ItemStack nether_quartz_ore() {
        ItemStack nether_quartz_ore =  new ItemStack(Material.NETHER_QUARTZ_ORE);
        ItemMeta meta = nether_quartz_ore.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Кварцевая руда").color(TextColor.color(gold)));
        nether_quartz_ore.setItemMeta(meta);
        return nether_quartz_ore;
    }

    public static ItemStack sandstone() {
        ItemStack sandstone =  new ItemStack(Material.SANDSTONE);
        ItemMeta meta = sandstone.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Песчаник").color(TextColor.color(gold)));
        sandstone.setItemMeta(meta);
        return sandstone;
    }

    public static ItemStack red_sandstone() {
        ItemStack red_sandstone =  new ItemStack(Material.RED_SANDSTONE);
        ItemMeta meta = red_sandstone.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Красный песчаник").color(TextColor.color(gold)));
        red_sandstone.setItemMeta(meta);
        return red_sandstone;
    }

    public static ItemStack quartz_block() {
        ItemStack quartz_block =  new ItemStack(Material.QUARTZ_BLOCK);
        ItemMeta meta = quartz_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Кварц").color(TextColor.color(gold)));
        quartz_block.setItemMeta(meta);
        return quartz_block;
    }

    public static ItemStack prismarine_bricks() {
        ItemStack prismarine_bricks =  new ItemStack(Material.PRISMARINE_BRICKS);
        ItemMeta meta = prismarine_bricks.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Призмариновый кирпич").color(TextColor.color(gold)));
        prismarine_bricks.setItemMeta(meta);
        return prismarine_bricks;
    }

    public static ItemStack prismarine() {
        ItemStack prismarine =  new ItemStack(Material.PRISMARINE);
        ItemMeta meta = prismarine.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Призмарин").color(TextColor.color(gold)));
        prismarine.setItemMeta(meta);
        return prismarine;
    }

    public static ItemStack purpur_block() {
        ItemStack purpur_block =  new ItemStack(Material.PURPUR_BLOCK);
        ItemMeta meta = purpur_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Пурпур").color(TextColor.color(gold)));
        purpur_block.setItemMeta(meta);
        return purpur_block;
    }

    public static ItemStack purpur_pillar() {
        ItemStack purpur_pillar =  new ItemStack(Material.PURPUR_PILLAR);
        ItemMeta meta = purpur_pillar.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Резной пурпур").color(TextColor.color(gold)));
        purpur_pillar.setItemMeta(meta);
        return purpur_pillar;
    }

    public static ItemStack end_stone_bricks() {
        ItemStack end_stone_bricks =  new ItemStack(Material.END_STONE_BRICKS);
        ItemMeta meta = end_stone_bricks.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Кирпич края").color(TextColor.color(gold)));
        end_stone_bricks.setItemMeta(meta);
        return end_stone_bricks;
    }

    public static ItemStack white_wool() {
        ItemStack white_wool =  new ItemStack(Material.WHITE_WOOL);
        ItemMeta meta = white_wool.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Шерсть").color(TextColor.color(gold)));
        white_wool.setItemMeta(meta);
        return white_wool;
    }

    public static ItemStack cobweb() {
        ItemStack cobweb =  new ItemStack(Material.COBWEB);
        ItemMeta meta = cobweb.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Паутина").color(TextColor.color(gold)));
        cobweb.setItemMeta(meta);
        return cobweb;
    }

    public static ItemStack terracotta() {
        ItemStack terracotta =  new ItemStack(Material.TERRACOTTA);
        ItemMeta meta = terracotta.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text( "Терракотта").color(TextColor.color(gold)));
        terracotta.setItemMeta(meta);
        return terracotta;
    }

    public static ItemStack brown_glazed_terracotta() {
        ItemStack brown_glazed_terracotta =  new ItemStack(Material.BROWN_GLAZED_TERRACOTTA);
        ItemMeta meta = brown_glazed_terracotta.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Керамика").color(TextColor.color(gold)));
        brown_glazed_terracotta.setItemMeta(meta);
        return brown_glazed_terracotta;
    }

    public static ItemStack ice() {
        ItemStack ice =  new ItemStack(Material.ICE);
        ItemMeta meta = ice.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Лёд").color(TextColor.color(gold)));
        ice.setItemMeta(meta);
        return ice;
    }

    public static ItemStack packed_ice() {
        ItemStack packed_ice =  new ItemStack(Material.PACKED_ICE);
        ItemMeta meta = packed_ice.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Плотный лёд").color(TextColor.color(gold)));
        packed_ice.setItemMeta(meta);
        return packed_ice;
    }

    public static ItemStack purple_terracotta() {
        ItemStack purple_terracotta =  new ItemStack(Material.PURPLE_TERRACOTTA);
        ItemMeta meta = purple_terracotta.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Фиолетовая терракотта").color(TextColor.color(gold)));
        purple_terracotta.setItemMeta(meta);
        return purple_terracotta;
    }

    public static ItemStack obsidian() {
        ItemStack obsidian =  new ItemStack(Material.OBSIDIAN);
        ItemMeta meta = obsidian.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Обсидиан").color(TextColor.color(gold)));
        obsidian.setItemMeta(meta);
        return obsidian;
    }

    public static ItemStack lapis_block() {
        ItemStack lapis_block =  new ItemStack(Material.LAPIS_BLOCK);
        ItemMeta meta = lapis_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Лазуритовый блок").color(TextColor.color(gold)));
        lapis_block.setItemMeta(meta);
        return lapis_block;
    }

    public static ItemStack iron_block() {
        ItemStack iron_block =  new ItemStack(Material.IRON_BLOCK);
        ItemMeta meta = iron_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Железный блок").color(TextColor.color(gold)));
        iron_block.setItemMeta(meta);
        return iron_block;
    }

    public static ItemStack gold_block() {
        ItemStack gold_block =  new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = gold_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Золотой блок").color(TextColor.color(gold)));
        gold_block.setItemMeta(meta);
        return gold_block;
    }

    public static ItemStack diamond_block() {
        ItemStack diamond_block =  new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta meta = diamond_block.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(Component.text("Алмазный блок").color(TextColor.color(gold)));
        diamond_block.setItemMeta(meta);
        return diamond_block;
    }
}
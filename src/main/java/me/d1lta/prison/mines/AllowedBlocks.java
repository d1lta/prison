package me.d1lta.prison.mines;

import java.util.List;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.Translate;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AllowedBlocks {

    static TextColor gold = TextColor.color(255,215,0);

    public static List<Material> blockMats = List.of(Material.DIRT, Material.GRAVEL, Material.SAND, Material.STONE, Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE,
            Material.WHITE_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE_POWDER, Material.NETHER_QUARTZ_ORE, Material.SOUL_SAND, Material.NETHERRACK,
            Material.SANDSTONE, Material.RED_SANDSTONE, Material.QUARTZ_BLOCK, Material.PRISMARINE_BRICKS, Material.PRISMARINE,
            Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.END_STONE_BRICKS, Material.WHITE_WOOL, Material.COBWEB, Material.TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA,
            Material.ICE, Material.PACKED_ICE, Material.PURPLE_TERRACOTTA, Material.OBSIDIAN, Material.LAPIS_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK,
            Material.OAK_WOOD);


    public static ItemStack getBlock(Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(CValues.get(Translate.block(material), gold).create());
        stack.setItemMeta(meta);
        return stack;
    }
}
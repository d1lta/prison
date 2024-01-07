package me.d1lta.prison.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MineUtils {

    public static Map<Location, Location> mineLocations() {
        Map<Location, Location> allowedLocations = new HashMap<>(Map.of(
                Mine.getLocation("dirtmine", 1), Mine.getLocation("dirtmine", 2),
                Mine.getLocation("stonemine", 1), Mine.getLocation("stonemine", 2),
                Mine.getLocation("concrete", 1), Mine.getLocation("concrete", 2),
                Mine.getLocation("hell", 1), Mine.getLocation("hell", 2),
                Mine.getLocation("desert", 1), Mine.getLocation("desert", 2),
                Mine.getLocation("quartzmine", 1), Mine.getLocation("quartzmine", 2),
                Mine.getLocation("end", 1), Mine.getLocation("end", 2),
                Mine.getLocation("spider", 1), Mine.getLocation("spider", 2),
                Mine.getLocation("quarry", 1), Mine.getLocation("quarry", 2),
                Mine.getLocation("icehills", 1), Mine.getLocation("icehills", 2)
        ));
        allowedLocations.putAll(Map.of(
                Mine.getLocation("obsmine", 1), Mine.getLocation("obsmine", 2),
                Mine.getLocation("valutjewelrymine", 1), Mine.getLocation("valutjewelrymine", 2),
                Mine.getLocation("valutdirtmine", 1), Mine.getLocation("valutdirtmine", 2)
        ));

        return allowedLocations;
    }

    public static ItemStack getPrisonBlock(Material mat) {
        return switch (mat) {
            case DIRT -> AllowedBlocks.dirt();
            case GRAVEL -> AllowedBlocks.gravel();
            case SAND -> AllowedBlocks.sand();
            case STONE -> AllowedBlocks.stone();
            case COAL_ORE -> AllowedBlocks.coal_ore();
            case IRON_ORE -> AllowedBlocks.iron_ore();
            case GOLD_ORE -> AllowedBlocks.gold_ore();
            case WHITE_CONCRETE_POWDER -> AllowedBlocks.white_concrete_block();
            case LIGHT_GRAY_CONCRETE_POWDER -> AllowedBlocks.light_gray_concrete_powder();
            case NETHERRACK -> AllowedBlocks.netherrack();
            case SOUL_SAND -> AllowedBlocks.soul_sand();
            case NETHER_QUARTZ_ORE -> AllowedBlocks.nether_quartz_ore();
            case SANDSTONE -> AllowedBlocks.sandstone();
            case RED_SANDSTONE -> AllowedBlocks.red_sandstone();
            case QUARTZ_BLOCK -> AllowedBlocks.quartz_block();
            case PRISMARINE -> AllowedBlocks.prismarine();
            case PRISMARINE_BRICKS -> AllowedBlocks.prismarine_bricks();
            case PURPUR_BLOCK -> AllowedBlocks.purpur_block();
            case PURPUR_PILLAR -> AllowedBlocks.purpur_pillar();
            case END_STONE_BRICKS -> AllowedBlocks.end_stone_bricks();
            case WHITE_WOOL -> AllowedBlocks.white_wool();
            case COBWEB -> AllowedBlocks.cobweb();
            case TERRACOTTA -> AllowedBlocks.terracotta();
            case BROWN_GLAZED_TERRACOTTA -> AllowedBlocks.brown_glazed_terracotta();
            case ICE -> AllowedBlocks.ice();
            case PACKED_ICE -> AllowedBlocks.packed_ice();
            case PURPLE_TERRACOTTA -> AllowedBlocks.purple_terracotta();
            case OBSIDIAN -> AllowedBlocks.obsidian();
            case IRON_BLOCK -> AllowedBlocks.iron_block();
            case LAPIS_BLOCK -> AllowedBlocks.lapis_block();
            case GOLD_BLOCK -> AllowedBlocks.gold_block();
            case DIAMOND_BLOCK -> AllowedBlocks.diamond_block();
            default -> new ItemStack(Material.AIR);
        };
    }

    public static boolean isAllowedToBreakBlock(Location blockLocation) {
        for (Entry<Location, Location> entry : MineUtils.mineLocations().entrySet()) {
            Location loc1 = entry.getKey();
            Location loc2 = entry.getValue();
            for (int x = (int) loc1.getX(); x <= loc2.getX(); x++) {
                for (int z = (int) loc1.getZ(); z <= loc2.getZ(); z++) {
                    for (int y = (int) loc1.getY(); y <= loc2.getY(); y++) {
                        if (Objects.equals(blockLocation, new Location(loc1.getWorld(), x, y, z))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}

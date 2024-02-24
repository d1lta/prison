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
        if (AllowedBlocks.blockMats.contains(mat)) {
            return AllowedBlocks.getBlock(mat);
        }
        return new ItemStack(Material.AIR);
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

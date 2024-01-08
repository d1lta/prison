package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class VaultJewelryMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("valutjewelrymine", 1),
                Mine.getLocation("valutjewelrymine", 2),
                Map.of(
                        "1:70", Material.LAPIS_BLOCK,
                        "71:84", Material.IRON_BLOCK,
                        "85:93", Material.GOLD_BLOCK,
                        "94:100", Material.DIAMOND_BLOCK
                ), false);
    }

}

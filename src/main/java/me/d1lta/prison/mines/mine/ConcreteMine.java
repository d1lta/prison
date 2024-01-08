package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class ConcreteMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("concrete", 1),
                Mine.getLocation("concrete", 2),
                Map.of(
                        "1:45", Material.LIGHT_GRAY_CONCRETE_POWDER,
                        "46:85", Material.WHITE_CONCRETE_POWDER,
                        "86:95", Material.IRON_ORE,
                        "96:100", Material.GOLD_ORE
                ), false);
    }

}

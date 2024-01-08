package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class QuartzMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("quartzmine", 1),
                Mine.getLocation("quartzmine", 2),
                Map.of(
                        "1:85", Material.QUARTZ_BLOCK,
                        "86:93", Material.PRISMARINE,
                        "94:100", Material.PRISMARINE_BRICKS
                ), false);
    }

}

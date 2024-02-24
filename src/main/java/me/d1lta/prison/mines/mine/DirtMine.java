package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class DirtMine {

    public static Map<String, Material> blocks = Map.of(
            "1:33", Material.DIRT,
            "34:70", Material.SAND,
            "71:100", Material.GRAVEL
    );

    public static void fill() {
        Mine.fill(
                Mine.getLocation("dirtmine", 1),
                Mine.getLocation("dirtmine", 2),
                blocks, false);
    }
}

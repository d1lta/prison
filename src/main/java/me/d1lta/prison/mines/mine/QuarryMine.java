package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class QuarryMine {

    public static Map<String, Material> blocks = Map.of(
            "1:97", Material.TERRACOTTA,
            "98:100", Material.BROWN_GLAZED_TERRACOTTA
    );

    public static void fill() {
        Mine.fill(
                Mine.getLocation("quarry", 1),
                Mine.getLocation("quarry", 2),
                blocks, false);
    }

}

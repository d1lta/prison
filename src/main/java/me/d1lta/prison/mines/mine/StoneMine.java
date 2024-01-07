package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class StoneMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("stonemine", 1),
                Mine.getLocation("stonemine", 2),
                Map.of(
                        "1:70", Material.STONE,
                        "71:84", Material.COAL_ORE,
                        "85:94", Material.IRON_ORE,
                        "95:100", Material.GOLD_ORE
                ));
    }
}

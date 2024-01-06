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
                        "1:80", Material.STONE,
                        "81:89", Material.COAL_ORE,
                        "90:94", Material.IRON_ORE,
                        "95:100", Material.GOLD_ORE
                ));
    }
}

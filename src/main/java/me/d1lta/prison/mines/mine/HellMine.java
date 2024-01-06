package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class HellMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("hell", 1),
                Mine.getLocation("hell", 2),
                Map.of(
                        "1:70", Material.NETHERRACK,
                        "71:80", Material.LAVA,
                        "81:90", Material.NETHER_QUARTZ_ORE,
                        "91:100", Material.SOUL_SAND
                ));
    }
}

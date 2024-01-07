package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class DesertMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("desert", 1),
                Mine.getLocation("desert", 2),
                Map.of(
                        "1:67", Material.SANDSTONE,
                        "68:100", Material.RED_SANDSTONE
                ));
    }

}

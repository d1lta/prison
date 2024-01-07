package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class VaultDirtMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("valutdirtmine", 1),
                Mine.getLocation("valutdirtmine", 2),
                Map.of(
                        "1:40", Material.DIRT,
                        "41:80", Material.SAND,
                        "81:100", Material.GRAVEL
                ));
    }

}

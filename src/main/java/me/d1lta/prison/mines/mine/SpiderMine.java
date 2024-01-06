package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class SpiderMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("spider", 1),
                Mine.getLocation("spider", 2),
                Map.of(
                        "1:85", Material.WHITE_WOOL,
                        "86:100", Material.COBWEB
                ));
    }

}

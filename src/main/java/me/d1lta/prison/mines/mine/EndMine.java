package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class EndMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("end", 1),
                Mine.getLocation("end", 2),
                Map.of(
                        "1:85", Material.PURPUR_BLOCK,
                        "86:100", Material.END_STONE_BRICKS
                ));
    }

}

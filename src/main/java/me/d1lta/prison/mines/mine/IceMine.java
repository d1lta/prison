package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class IceMine {

    public static Map<String, Material> blocks = Map.of(
            "1:50", Material.ICE,
            "51:100", Material.PACKED_ICE
    );

    public static void fill() {
        Mine.fill(
                Mine.getLocation("icehills", 1),
                Mine.getLocation("icehills", 2),
                blocks, false);
    }
}

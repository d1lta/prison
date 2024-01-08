package me.d1lta.prison.mines.mine;

import java.util.Map;
import me.d1lta.prison.mines.Mine;
import org.bukkit.Material;

public class ObsMine {

    public static void fill() {
        Mine.fill(
                Mine.getLocation("obsmine", 1),
                Mine.getLocation("obsmine", 2),
                Map.of(
                        "1:90", Material.PURPLE_TERRACOTTA,
                        "91:100", Material.OBSIDIAN
                ), false);
    }

}

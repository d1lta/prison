package me.d1lta.prison.mines;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import me.d1lta.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class Mine {

    public static void fill(@NotNull Location loc1, @NotNull Location loc2, @NotNull Map<String, Material> materials, boolean yummy) {
        for (int x = (int) loc1.getX(); x <= loc2.getX(); x++) {
            for (int z = (int) loc1.getZ(); z <= loc2.getZ(); z++) {
                for (int y = (int) loc1.getY(); y <= loc2.getY(); y++) {
                    if (yummy && y == loc2.getY()) {
                        Objects.requireNonNull(loc1.getWorld()).getBlockAt(x, y, z).setType(materials.get(materials.keySet().stream().findFirst().get()));
                        continue;
                    }
                    Objects.requireNonNull(loc1.getWorld()).getBlockAt(x, y, z).setType(getMaterial(materials));
                }
            }
        }
    }

    private static Material getMaterial(Map<String, Material> map) {
        int i = new Random().nextInt(1, 101);
        for (String it : map.keySet()) {
            String[] chances = it.split(":");
            if (i >= Integer.parseInt(chances[0]) && i <= Integer.parseInt(chances[1])) {
                return map.get(it);
            }
        }
        return Material.BEDROCK;
    }

    public static Location getLocation(String mineName, int loc) {
        String cfg = "mines." + mineName + ".";
        return new Location(
                Bukkit.getWorld(Objects.requireNonNull(Main.config.getConfig().getString(cfg + "world"))),
                Main.config.getConfig().getInt(cfg + "loc" + loc + ".x"),
                Main.config.getConfig().getInt(cfg + "loc" + loc +  ".y"),
                Main.config.getConfig().getInt(cfg + "loc" + loc +  ".z")
        );
    }
}

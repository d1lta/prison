package me.d1lta.prison.utils;

import me.d1lta.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static Location spawnPoint(String name) {
        String cfg = "spawnpoints." + name + ".";
        return new Location(
                Bukkit.getWorld(name),
                Main.config.getConfig().getDouble(cfg + "x"),
                Main.config.getConfig().getDouble(cfg + "y"),
                Main.config.getConfig().getDouble(cfg + "z"),
                Main.config.getConfig().getInt(cfg + "yaw"),
                Main.config.getConfig().getInt(cfg + "pitch")
        );
    }

}

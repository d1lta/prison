package me.d1lta.prison.warzone;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WarzoneCapture implements Listener {

    public static HastePoint haste = new HastePoint();
    public static MoneyPoint money = new MoneyPoint();
    public static BlocksPoint blocks = new BlocksPoint();
    public static SpeedPoint speed = new SpeedPoint();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getPlayer().getLocation().getWorld().getName().equals("warzone")) {
            LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
            String point = getPointType(pl.getLocation());
            if (point == null) {
                point = "null";
            }
            if (point.equals(haste.type)) {
                logic(pl, haste);
            }
            if (point.equals(money.type)) {
                logic(pl, money);
            }
            if (point.equals(blocks.type)) {
                logic(pl, blocks);
            }
            if (point.equals(speed.type)) {
                logic(pl, speed);
            }
        }
    }

    private static void logic(LittlePlayer pl, Point point) {
        if (pl.getFaction() == Factions.NO_FACTION) {
            pl.sendMessage("Вы без фракции...");
            return;
        }
        if (point.capturedBy == null) {
            if (point.players.size() != 0) {
                List<LittlePlayer> list = new ArrayList<>();
                point.players.forEach(it -> list.add(new LittlePlayer(it)));
                if (!list.contains(pl)) {
                    list.add(pl);
                }
                if (!Factions.isPlayersInSingleFaction(list)) {
                    point.refreshColor(getCentralPoint(point.type));
                    point.players.clear();
                    point.stopCapture();
                }
            }
            if (!point.players.contains(pl.uuid)) {
                point.players.add(pl.uuid);
                point.startCapture(pl.getFaction());
            }
        }
    }

    public static String getPointType(Location plLoc) {
        for (String it : Main.config.getConfig().getConfigurationSection("warzonepoints").getKeys(false)) {
            if (onPoint(plLoc, getCentralPoint(it))) {
                return it;
            }
        }
        return null;
    }

    public static Point getPoint(Location plLoc) {
        for (String it : Main.config.getConfig().getConfigurationSection("warzonepoints").getKeys(false)) {
            if (getPointType(plLoc).equals(it)) {
                return switch (it) {
                    case "haste" -> haste;
                    case "speed" -> speed;
                    case "blocks" -> blocks;
                    case "money" -> money;
                    default -> null;
                };
            }
        }
        return null;
    }

    public static Location getCentralPoint(String type) {
        return new Location(Bukkit.getWorld("warzone"),
                Main.config.getConfig().getInt("warzonepoints." + type + ".x"),
                Main.config.getConfig().getInt("warzonepoints." + type + ".y"),
                Main.config.getConfig().getInt("warzonepoints." + type + ".z"));
    }

    private static boolean onPoint(Location plLoc, Location loc) {
        if (plLoc.getX() < getMaxX(loc) && plLoc.getX() > getMinX(loc)) {
            return plLoc.getZ() < getMaxZ(loc) && plLoc.getZ() > getMinZ(loc);
        }
        return false;
    }

    private static int getMaxX(Location loc) {
        return loc.getBlockX() + 2;
    }

    private static int getMinX(Location loc) {
        return loc.getBlockX() - 1;
    }

    private static int getMaxZ(Location loc) {
        return loc.getBlockZ() + 2;
    }

    private static int getMinZ(Location loc) {
        return loc.getBlockZ() - 1;
    }
}

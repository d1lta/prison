package me.d1lta.prison.boosters;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LittlePlayer;

public class BoosterHandler {

    public static void addBoost(LittlePlayer pl, String type, String multiplier, int amount, boolean local) {
        multiplier = multiplier.replaceAll("\\.", ",");
        String path = "";
        if (local) {
            path = pl.uuid + ".local." + type + "." + multiplier;
        } else {
            path = pl.uuid + ".global." + type + "." + multiplier;
        }
        if (Main.boosters.getConfig().contains(path)) {
            Main.boosters.getConfig().set(path + ".amount", Main.boosters.getConfig().getInt(path + ".amount") + amount);
            Main.boosters.getConfig().set(path + ".local", local);
        } else {
            Main.boosters.getConfig().set(path + ".amount", amount);
            Main.boosters.getConfig().set(path + ".local", local);
        }
        Main.boosters.saveConfig();
    }

    public static List<Boost> getBoosts(LittlePlayer pl) {
        List<Boost> boosters = new ArrayList<>();
        List<String> paths = List.of(
                pl.uuid + ".local." + ".blocks",
                pl.uuid + ".local." + ".money",
                pl.uuid + ".global." + ".blocks",
                pl.uuid + ".global." + ".money"
        );
        for (String path: paths) {
            if (Main.boosters.getConfig().getConfigurationSection(path) == null) {
                continue;
            }
            for (String boost: Main.boosters.getConfig().getConfigurationSection(path).getKeys(false)) {
                boost = boost.replaceAll(",", ".");
                if (path.contains("local") && path.contains("blocks")) {
                    boosters.add(new Boost(true, "blocks", Double.parseDouble(boost), Main.boosters.getConfig().getInt(path + "." + boost.replaceAll("\\.",",") + ".amount")));
                }
                if (path.contains("global") && path.contains("blocks")) {
                    boosters.add(new Boost(false, "blocks", Double.parseDouble(boost), Main.boosters.getConfig().getInt(path + "." + boost.replaceAll("\\.",",") + ".amount")));
                }
                if (path.contains("local") && path.contains("money")) {
                    boosters.add(new Boost(true, "money", Double.parseDouble(boost), Main.boosters.getConfig().getInt(path + "." + boost.replaceAll("\\.",",") + ".amount")));
                }
                if (path.contains("global") && path.contains("money")) {
                    boosters.add(new Boost(false, "money", Double.parseDouble(boost), Main.boosters.getConfig().getInt(path + "." + boost.replaceAll("\\.",",") + ".amount")));
                }
            }
        }
        return boosters;
    }

}

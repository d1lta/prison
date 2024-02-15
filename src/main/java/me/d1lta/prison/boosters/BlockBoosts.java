package me.d1lta.prison.boosters;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;

public class BlockBoosts {

    public static Map<UUID, Double> localBoosters = new HashMap<>();

    public static boolean globalBoost = false;
    public static double globalBoostMultiplier = 1.0;

    public static boolean addBoost(LittlePlayer pl, Double amount) {
        if (localBoosters.containsKey(pl.uuid)) {
            return false;
        } else {
            localBoosters.put(pl.uuid, amount);
            return true;
        }
    }

    public static void globalBoost(Boost boost) {
        if (!boost.local) {
            globalBoost = true;
            globalBoostMultiplier = boost.multiplier;
            toCancelGlobalBoost();
        }
    }

    public static void localBoost(LittlePlayer pl, double multiplier) {
        BlockBoosts.addBoost(pl, multiplier);
        toCancelLocalBoost(pl.uuid);
    }

    private static void toCancelLocalBoost(UUID uuid) {
        AtomicInteger count = new AtomicInteger(0);
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            localBoosters.remove(uuid);
            new LittlePlayer(uuid).sendMessage("Ваша локалка закончилась");
        }, 20L * 30L * 60L);
    }

    private static void toCancelGlobalBoost() {
        AtomicInteger count = new AtomicInteger(0);
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            globalBoost = false;
            globalBoostMultiplier = 1.0;
            Bukkit.getOnlinePlayers().forEach(it -> it.sendMessage("глобалка офф"));
        }, 20L * 30L * 60L);
    }
}

package me.d1lta.prison;

import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleport {

    private static int taskID;

    public static void tp(Player player, Location from, Location destination) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.teleport(destination);
            return;
        }
        AtomicInteger count = new AtomicInteger(5);
        taskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            double os = 0;
            os += Math.abs(from.getX() - player.getX());
            os += Math.abs(from.getY() - player.getY());
            os += Math.abs(from.getZ() - player.getZ());
            if (os >= 4) {
                player.sendTitle("","Телепортация отменена.", 0,20,15);
                Bukkit.getScheduler().cancelTask(taskID);
                return;
            }
            if (count.get() == 0) {
                player.teleport(destination);
                Bukkit.getScheduler().cancelTask(taskID);
                return;
            }
            player.sendTitle("Телепортация...", "осталось " + count.get() + " секунд.", 0,20,5);
            count.set(count.get() - 1);
        },20L, 20L).getTaskId();
    }

}
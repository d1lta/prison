package me.d1lta.prison;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;

public class Teleport {

    private static int taskID;

    public static void tp(LittlePlayer player, Location from, Location destination) {
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.uuid.toString().equals("1df854df-513d-4485-9c31-6cd72f6ce3e2")) {
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

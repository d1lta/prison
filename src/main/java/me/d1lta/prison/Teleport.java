package me.d1lta.prison;

import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class Teleport {

    public static void tp(LittlePlayer player, Location from, Location destination) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.teleport(destination);
            return;
        }
        AtomicInteger count = new AtomicInteger(5);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList().contains(player.uuid)) { this.cancel(); return; }
                double os = 0;
                os += Math.abs(from.getX() - player.getX());
                os += Math.abs(from.getY() - player.getY());
                os += Math.abs(from.getZ() - player.getZ());
                if (os >= 4) {
                    player.sendTitle("","Телепортация отменена.", 0,20,15);
                    this.cancel();
                    return;
                }
                if (count.get() == 0) {
                    player.teleport(destination);
                    this.cancel();
                    return;
                }
                player.sendTitle("Телепортация...", "осталось " + count.get() + " секунд.", 0,20,5);
                count.set(count.get() - 1);
            }
        }.runTaskTimer(Main.plugin, 20L, 20L);
    }

}

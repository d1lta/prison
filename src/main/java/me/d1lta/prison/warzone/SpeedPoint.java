package me.d1lta.prison.warzone;

import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPoint extends Point {

    private static int taskID;

    public SpeedPoint() {
        super(() -> {
            AtomicInteger count = new AtomicInteger(0);
            Bukkit.broadcastMessage("speed point bonus available for " + WarzoneCapture.speed.capturedBy.getName());
            taskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
                if (WarzoneCapture.speed.capturedBy == null) {
                    Bukkit.getScheduler().cancelTask(taskID);
                    return;
                }
                if (count.get() % 10 == 0) {
                    for (LittlePlayer player : Factions.getPlayersInFaction(WarzoneCapture.speed.capturedBy)) {
                        player.giveEffect(new PotionEffect(PotionEffectType.SPEED, 3 * 20, 1));
                    }
                }
                count.set(count.get() + 1);
            }, 20L, 20L).getTaskId();
        });
        this.type = "speed";
    }
}

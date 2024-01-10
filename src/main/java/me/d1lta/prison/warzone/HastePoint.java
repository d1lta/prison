package me.d1lta.prison.warzone;

import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HastePoint extends Point {

    private static int taskID;

    public HastePoint() {
        super(() -> {
            AtomicInteger count = new AtomicInteger(0);
            Bukkit.broadcastMessage("haste point bonus available for " + WarzoneCapture.haste.capturedBy.getName());
            taskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
                if (WarzoneCapture.haste.capturedBy == null) {
                    Bukkit.getScheduler().cancelTask(taskID);
                    return;
                }
                if (count.get() % 10 == 0) {
                    for (LittlePlayer player : Factions.getPlayersInFaction(WarzoneCapture.haste.capturedBy)) {
                        player.giveEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 3 * 20, 3));
                    }
                }
                count.set(count.get() + 1);
            }, 20L, 20L).getTaskId();
        });
        this.type = "haste";
    }
}

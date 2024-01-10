package me.d1lta.prison.arch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.warzone.WarzoneCapture;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Haste {

    Factions capturingBy = Factions.NO_FACTION;
    Factions capturedBy = null;
    AtomicInteger count = new AtomicInteger(0);
    AtomicInteger toReset = new AtomicInteger(10);
    int maxCaptureTime = 8;
    boolean capturing = false;
    private static int taskID;
    List<UUID> players = new ArrayList<>();

    public void startCapture(Factions faction) {
        this.capturingBy = faction;
        capturing = true;
        taskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            players.removeIf(it -> !ifPlayerNear(new LittlePlayer(it)));
            if (players.size() != 0) {
                if (count.get() == maxCaptureTime) {
                    count.set(0);
                    this.capturing = false;
                    this.players.clear();
                    this.capturedBy = capturingBy;
                    this.capturingBy = Factions.NO_FACTION;
                    WarzoneCapture.getCentralPoint("haste").getBlock().setType(this.capturedBy.getWarzoneGlassMat());
                    Bukkit.broadcastMessage("HASTE captured by " + capturedBy.getName());
                    startCountToReset();
                    stopCapture();
                    return;
                }
            } else {
                refreshColor(WarzoneCapture.getCentralPoint("haste"));
                stopCapture();
                return;
            }
            count.set(count.get() + 1);
            colorize(WarzoneCapture.getCentralPoint("haste"), count.get(), capturingBy.getWarzoneBlockMat());
            Bukkit.broadcastMessage(count.get() + "");
        },20L, 20L).getTaskId();
    }

    private boolean ifPlayerNear(LittlePlayer player) {
        return Objects.equals(WarzoneCapture.getPointType(player.getLocation()), "haste");
    }

    public void stopCapture() {
        Bukkit.broadcastMessage("capture stopped");
        this.count.set(0);
        Bukkit.getScheduler().cancelTask(taskID);
    }

    private void refreshColor(Location cp) {
        for (int i = 1; i <= 8; i++) {
            colorize(cp, i, Factions.NO_FACTION.getWarzoneBlockMat());
        }
    }

    private void colorize(Location cp, int count, Material material) {
        switch (count) {
            case 1: new Location(cp.getWorld(), cp.getBlockX(), cp.getBlockY(), cp.getBlockZ() - 1).getBlock().setType(material); return;
            case 2: new Location(cp.getWorld(), cp.getBlockX() - 1, cp.getBlockY(), cp.getBlockZ() - 1).getBlock().setType(material); return;
            case 3: new Location(cp.getWorld(), cp.getBlockX() - 1, cp.getBlockY(), cp.getBlockZ()).getBlock().setType(material); return;
            case 4: new Location(cp.getWorld(), cp.getBlockX() - 1, cp.getBlockY(), cp.getBlockZ() + 1).getBlock().setType(material); return;
            case 5: new Location(cp.getWorld(), cp.getBlockX(), cp.getBlockY(), cp.getBlockZ() + 1).getBlock().setType(material); return;
            case 6: new Location(cp.getWorld(), cp.getBlockX() + 1, cp.getBlockY(), cp.getBlockZ() + 1).getBlock().setType(material); return;
            case 7: new Location(cp.getWorld(), cp.getBlockX() + 1, cp.getBlockY(), cp.getBlockZ()).getBlock().setType(material); return;
            case 8: new Location(cp.getWorld(), cp.getBlockX() + 1, cp.getBlockY(), cp.getBlockZ() - 1).getBlock().setType(material);
        }
    }

    private static int resetTaskID;
    private void startCountToReset() {
        resetTaskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            if (toReset.get() == 0) {
                toReset.set(10);
                capturedBy = null;
                WarzoneCapture.getCentralPoint("haste").getBlock().setType(Factions.NO_FACTION.getWarzoneGlassMat());
                Bukkit.broadcastMessage(toReset.get() + "<-");
                Bukkit.getScheduler().cancelTask(resetTaskID);
                refreshColor(WarzoneCapture.getCentralPoint("haste"));
                return;
            }
            toReset.set(toReset.get() - 1);
        }, 20L, 20L).getTaskId();
    }
}

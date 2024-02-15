package me.d1lta.prison.warzone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public abstract class Point {

    public String type;
    public Factions capturingBy = Factions.NO_FACTION;
    public Factions capturedBy = null;
    public AtomicInteger count = new AtomicInteger(0);
    public AtomicInteger toReset = new AtomicInteger(10 * 60);
    int maxCaptureTime = 8;
    boolean capturing = false;
    private int taskID;
    public List<UUID> players = new ArrayList<>();
    Runnable function;

    List<UUID> playersOnPoint = new ArrayList<>(); // для того чтобы в чат не срало хуйнёй от ивента

    public Point(Runnable function) {
        this.function = function;
        //listClear();
    }

    public void startCapture(Factions faction) {
        if (capturing) { return; }
        this.capturingBy = faction;
        this.capturing = true;
        this.taskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            this.players.removeIf(it -> !ifPlayerNear(new LittlePlayer(it)));
            if (this.players.size() != 0) {
                if (this.count.get() == this.maxCaptureTime) {
                    this.count.set(0);
                    this.capturing = false;
                    this.players.clear();
                    this.capturedBy = capturingBy;
                    this.capturingBy = null;
                    WarzoneCapture.getCentralPoint(type).getBlock().setType(this.capturedBy.getWarzoneGlassMat());
                    this.factionBonus();
                    this.startCountToReset();
                    this.stopCapture();
                    return;
                }
            } else {
                this.refreshColor(WarzoneCapture.getCentralPoint(this.type));
                this.stopCapture();
                return;
            }
            this.count.set(this.count.get() + 1);
            this.colorize(WarzoneCapture.getCentralPoint(type), this.count.get(), this.capturingBy.getWarzoneBlockMat());
        },20L, 20L).getTaskId();
    }

    private boolean ifPlayerNear(LittlePlayer player) {
        return Objects.equals(WarzoneCapture.getPointType(player.getLocation()), this.type);
    }

    public void stopCapture() {
        Bukkit.broadcastMessage("capture stopped");
        this.count.set(0);
        this.capturing = false;
        this.capturingBy = null;
        Bukkit.getScheduler().cancelTask(this.taskID);
    }

    public void refreshColor(Location cp) {
        for (int i = 1; i <= 8; i++) {
            this.colorize(cp, i, Factions.NO_FACTION.getWarzoneBlockMat());
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

    private void listClear() {
        Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            if (this.playersOnPoint.size() != 0) {
                for (UUID it : this.playersOnPoint) { // err
                    if (WarzoneCapture.getPointType(Bukkit.getPlayer(it).getLocation()) == null) {
                        this.playersOnPoint.remove(it);
                    }
                }
            }
        }, 20L, 20L);
    }

    public int getTimeToReset() {
        return this.toReset.get();
    }

    private int resetTaskID;
    private void startCountToReset() {
        this.resetTaskID = Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            if (this.toReset.get() == 0) {
                this.toReset.set(10 * 60);
                this.capturedBy = null;
                this.capturingBy = null;
                this.players.clear();
                WarzoneCapture.getCentralPoint(this.type).getBlock().setType(Factions.NO_FACTION.getWarzoneGlassMat());
                Bukkit.broadcastMessage(this.toReset.get() + "<-");
                Bukkit.getScheduler().cancelTask(this.resetTaskID);
                this.refreshColor(WarzoneCapture.getCentralPoint(this.type));
                return;
            }
            this.toReset.set(this.toReset.get() - 1);
        }, 20L, 20L).getTaskId();
    }

    private void factionBonus() {
        this.function.run();
    }

}

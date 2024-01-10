package me.d1lta.prison.commands;

import me.d1lta.prison.utils.NBT;
import me.d1lta.prison.warzone.Point;
import me.d1lta.prison.warzone.WarzoneCapture;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Point point = null;
        switch (args[0]) {
            case "speed": point = WarzoneCapture.speed; break;
            case "haste": point = WarzoneCapture.haste; break;
            case "blocks": point = WarzoneCapture.blocks; break;
            case "money": point = WarzoneCapture.money; break;
        }
        if (point.players != null) {
            Bukkit.broadcastMessage("players:" + point.players.toString());
        } else {
            Bukkit.broadcastMessage("players: null");
        }
        if (point.type != null) {
            Bukkit.broadcastMessage("type:" + point.type);
        } else {
            Bukkit.broadcastMessage("type: null");
        }
        if (point.capturedBy != null) {
            Bukkit.broadcastMessage("capturedBy:" + point.capturedBy.getName());
        } else {
            Bukkit.broadcastMessage("capturedBy: null");
        }
        if (point.capturingBy != null) {
            Bukkit.broadcastMessage("capturingBy:" + point.capturingBy.getName());
        } else {
            Bukkit.broadcastMessage("capturingBy: null");
        }
        Bukkit.broadcastMessage("count:" + point.count.get());
        return true;
    }
}

package me.d1lta.prison.commands;

import static me.d1lta.prison.utils.LocationUtils.spawnPoint;

import me.d1lta.prison.Teleport;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            Teleport.tp(pl, pl.getLocation(), spawnPoint("hub"));
            return true;
        }
        return false;
    }
}

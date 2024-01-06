package me.d1lta.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldTp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) { return false; }
        if (args.length == 1) {
            Bukkit.getWorlds().forEach(it -> {
                if (it.getName().equals(args[0])) {
                    ((Player) sender).teleport(new Location(Bukkit.getWorld(args[0]),0.5,65,0.5));
                }
            });

        }
        return false;
    }
}

package me.d1lta.prison.commands;

import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        NBT.getKeys(((Player) sender).getInventory().getItemInMainHand()).forEach(Bukkit::broadcastMessage);
        return true;
    }



}

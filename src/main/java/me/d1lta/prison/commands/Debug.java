package me.d1lta.prison.commands;

import me.d1lta.prison.PlayerValues;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Bukkit.broadcastMessage(String.format("blocks: %d | money: %d | certainBlocks: %d", PlayerValues.blocks.size(), PlayerValues.money.size(), PlayerValues.certainBlocks.size()));
        return true;
    }



}

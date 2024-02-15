package me.d1lta.prison.commands;

import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
        NBT.getKeys(pl.getItemInMainHand()).forEach(it -> {
            pl.sendMessage(it + ": " + NBT.getStringNBT(pl.getItemInMainHand(), it));
        });
        return true;
    }
}

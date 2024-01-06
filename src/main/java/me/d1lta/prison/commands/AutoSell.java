package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AutoSell implements CommandExecutor {

    public static List<Player> players = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            if (players.contains(pl)) {
                pl.sendMessage("Автопродажа выключена.");
                players.remove(pl);
            } else {
                pl.sendMessage("Автопродажа включена.");
                players.add(pl);
            }
        }
        return false;
    }
}

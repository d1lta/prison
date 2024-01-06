package me.d1lta.prison.commands;

import me.d1lta.prison.items.Tools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            if (args.length == 1) {
                switch (args[0]) {
                    case "shovel" -> pl.getInventory().addItem(Tools.shovel());
                    case "pickaxe" -> pl.getInventory().addItem(Tools.pickaxe());
                    case "axe" -> pl.getInventory().addItem(Tools.axe());
                    case "shears" -> pl.getInventory().addItem(Tools.shears());
                }
            }
        }
        return false;
    }
}

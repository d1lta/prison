package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Config implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player pl) {
            if (!pl.isOp()) {
                pl.sendMessage("?");
                return true;
            }
            if (args.length == 1 && (args[0].equals("rl") || args[0].equals("reload"))) {
                Main.config.reloadConfig();
                pl.sendMessage(ChatColor.GOLD + "Конфиг обновлён.");
            } else {
                pl.sendMessage("?");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            Main.config.reloadConfig();
            sender.sendMessage(ChatColor.WHITE + "Конфиг обновлён.\n");
        }
        return true;
    }
}

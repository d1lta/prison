package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Config implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (args.length == 1 && (args[0].equals("rl") || args[0].equals("reload"))) {
                Main.config.reloadConfig();
                pl.sendMessage(CValues.get("Конфиг обновлён", 255, 214, 0).create());
            } else {
                pl.sendMessage(CValues.get("?", 255, 0, 0).create());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            Main.config.reloadConfig();
            sender.sendMessage(CValues.get("Конфиг обновлён", 255, 214, 0).create());
        }
        return true;
    }
}

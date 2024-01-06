package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import me.d1lta.prison.worldGenerators.VoidGen;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WorldCreate implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            if (args[0].equals("create")) {
                new WorldCreator(args[1]).generator(new VoidGen()).createWorld();
                Main.config.getConfig().set("worlds." + args[1] + ".creator", sender.getName());
                Main.config.saveConfig();
                return true;
            }
        }
        return false;
    }
}

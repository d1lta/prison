package me.d1lta.prison.commands;

import java.util.Objects;
import me.d1lta.prison.Jedis;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AdmJedis implements CommandExecutor {

    // jedis get <player> <values>
    // jedis set <player> <values> <value>

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 3) {
            if (args[0].equals("get")) {
                sender.sendMessage(Jedis.get(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId() + "." + args[2]));
                return true;
            }
        }
        if (args.length == 4) {
            if (args[0].equals("set")) {
                Jedis.set(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId() + "." + args[2], args[3]);
                sender.sendMessage(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId() + "." + args[2] + " = " + args[3]);
                return true;
            }
        }
        return false;
    }
}

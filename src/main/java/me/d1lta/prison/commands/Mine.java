package me.d1lta.prison.commands;

import static me.d1lta.prison.utils.LocationUtils.spawnPoint;

import java.util.Arrays;
import java.util.Map;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Teleport;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Mine implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            if (args.length == 0) {
                pl.sendMessage("this command isn't available");
                return true;
            }
            if (args.length == 1 && args[0].matches("-?(0|[1-9]\\d*)")) {
                int lvl = Integer.parseInt(Jedis.get(pl.getUniqueId() + ".lvl"));
                Map<Integer, String> mines = Map.of(
                        3, "stonemine",
                        5, "concrete",
                        7, "hell",
                        9, "desert",
                        12, "quartzmine",
                        14, "end",
                        16, "spider",
                        19, "quarry",
                        21, "icehills",
                        23,"obsmine");
                if (!mines.containsKey(Integer.parseInt(args[0]))) {
                    pl.sendMessage("Такой шахты не знаю.");
                    return true;
                }
                if (lvl >= Integer.parseInt(args[0]) || pl.isOp()) {
                    Teleport.tp(pl, pl.getLocation(), spawnPoint(mines.get(Integer.parseInt(args[0]))));
                    return true;
                }
            } else {
                pl.sendMessage("?");
                return true;
            }
        }
        return false;
    }
}

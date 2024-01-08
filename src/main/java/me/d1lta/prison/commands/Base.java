package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import me.d1lta.prison.Teleport;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Base implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (pl.getLevel() < 5) {
                pl.sendMessage("Маленький уровень");
                return true;
            }
            if (pl.getFaction().equals(Factions.NO_FACTION)) {
                pl.sendMessage("Вы без фракции!");
                return true;
            }
            String path = "spawnpoints.base." + pl.getFaction().getConfigName() + ".";
            Teleport.tp(
                    pl, pl.getLocation(), new Location(Bukkit.getWorld("base"), getValueD(path, "x"), getValueD(path, "y"), getValueD(path, "z"),
                            getValueF(path, "yaw"), getValueF(path, "pitch"))
            );
            return true;
        }
        return false;
    }

    private Double getValueD(String path, String value) {
        Bukkit.broadcastMessage(path + value + ": " + Main.config.getConfig().get(path + value));
        return Double.parseDouble(String.valueOf(Main.config.getConfig().get(path + value)));
    }

    private Float getValueF(String path, String value) {
        return Float.parseFloat(String.valueOf(Main.config.getConfig().get(path + value)));
    }
}

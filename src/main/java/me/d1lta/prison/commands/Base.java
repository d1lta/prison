package me.d1lta.prison.commands;

import static me.d1lta.prison.utils.NumberUtils.getValueD;
import static me.d1lta.prison.utils.NumberUtils.getValueF;

import me.d1lta.prison.Teleport;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Base implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (pl.getLevel() < 5) {
                pl.sendMessage(CValues.get("У вас слишком маленький уровень!", 255, 214, 0).create());
                return true;
            }
            if (pl.getFaction().equals(Factions.NO_FACTION)) {
                pl.sendMessage(CValues.get("Вы без фракции!", 255, 214, 0).create());
                return true;
            }
            String path = "spawnpoints.base." + pl.getFaction().getConfigName() + ".";
            Teleport.tp(pl, pl.getLocation(), new Location(Bukkit.getWorld("base"), getValueD(path, "x"), getValueD(path, "y"), getValueD(path, "z"), getValueF(path, "yaw"), getValueF(path, "pitch")));
            return true;
        }
        return false;
    }
}

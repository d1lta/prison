package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AutoSell implements CommandExecutor {

    public static List<UUID> uuids = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (uuids.contains(pl.uuid)) {
                pl.sendMessage(CValues.get("Автопродажа выключена!", 176, 175, 111).create());
                uuids.remove(pl.uuid);
            } else {
                pl.sendMessage(CValues.get("Автопродажа включена!", 176, 175, 111).create());
                uuids.add(pl.uuid);
            }
            pl.changeAutoSell();
        }
        return true;
    }
}

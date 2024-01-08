package me.d1lta.prison.commands;

import me.d1lta.prison.mobs.Rat;
import me.d1lta.prison.mobs.bosses.Vindicator;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SummonMob implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            switch (args[0]) {
                case "rat": new Rat(pl.getLocation());
                case "vindicator": new Vindicator(pl.getLocation());

            }
        }
        return false;
    }
}

package me.d1lta.prison.commands;

import me.d1lta.prison.enchants.book.HammerBook;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Enchant implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (args.length == 2 && pl.isOp()) {
                switch (args[0]) {
                    case "hammer": {
                        pl.giveItem(new HammerBook(Integer.parseInt(args[1])).getBook());
                    }
                }
                return true;
            }
        }
        return false;
    }
}

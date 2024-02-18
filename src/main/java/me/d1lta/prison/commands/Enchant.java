package me.d1lta.prison.commands;

import java.util.Objects;
import me.d1lta.prison.enums.Enchantments;
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
            if (args.length == 3 && pl.isOp()) {
                pl.giveItem(Objects.requireNonNull(Enchantments.getEnchantment(args[0]).getBook(Integer.parseInt(args[1]), Integer.parseInt(args[2]))).getBook());
                return true;
            }
        }
        return false;
    }
}

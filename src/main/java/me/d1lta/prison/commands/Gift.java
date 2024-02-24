package me.d1lta.prison.commands;

import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

public class Gift implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
                LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
                LittlePlayer receiver = new LittlePlayer(Bukkit.getPlayer(args[0]).getUniqueId());
                if (!CheckUtils.checkForNull(pl.getItemInMainHand()) && distanceSquared(pl.getLocation(), receiver.getLocation()) > 10) { return false; }
                ItemStack stack = pl.getItemInMainHand().clone();
                pl.sendMessage(DComponent.create("Вы передали ")
                        .append(stack.displayName())
                        .append(DComponent.create(" игроку "))
                        .append(DComponent.create(receiver.getName())));
                pl.getItemInMainHand().setAmount(0);
                receiver.giveItem(stack);
                receiver.sendMessage(DComponent.create(pl.getName() + " передал вам ").append(stack.displayName()));

            }
        }
        return true;
    }

    private static double distanceSquared (Location first, Location second) {
        return NumberConversions.square(first.getX() - second.getX()) + NumberConversions.square(first.getY() - second.getY()) + NumberConversions.square(first.getZ() - second.getZ());
    }
}

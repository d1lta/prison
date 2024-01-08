package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
            if (args.length == 1 && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
                LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
                LittlePlayer receiver = new LittlePlayer(Bukkit.getPlayer(args[0]).getUniqueId());
                ItemStack stack = pl.getItemInMainHand().clone();
                if (stack != null || stack.getType().equals(Material.AIR)) {
                    if (distanceSquared(pl.getLocation(), receiver.getLocation()) < 5) {

                        pl.sendMessage(ComponentUtils.component("Вы передали ")
                                .append(stack.getItemMeta().displayName())
                                .append(ComponentUtils.component(" игроку "))
                                .append(ComponentUtils.component(receiver.getName())));
                        pl.getItemInMainHand().setAmount(0);
                        receiver.giveItem(stack);
                        receiver.sendMessage(ComponentUtils.component(pl.getName() + " передал вам ")
                                .append(stack.getItemMeta().displayName()));
                    }
                }
            }
        }
        return false;
    }

    private double distanceSquared (Location first, Location second){
        return NumberConversions.square(first.getX() - second.getX()) + NumberConversions.square(first.getY() - second.getY()) + NumberConversions.square(first.getZ() - second.getZ());
    }
}

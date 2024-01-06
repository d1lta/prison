package me.d1lta.prison.commands;

import me.d1lta.prison.utils.NBT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Debug implements CommandExecutor {

    ItemStack stack1;
    ItemStack stack2;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            if (args[0].equals("stack1")) {
                stack1 = pl.getInventory().getItemInMainHand();
            }
            if (args[0].equals("stack2")) {
                stack2 = pl.getInventory().getItemInMainHand();
            }
            if (args[0].equals("compare")) {
                pl.sendMessage(String.valueOf(stack1.isSimilar(stack2)));
            }
        }
        return false;
    }
}

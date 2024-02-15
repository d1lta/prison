package me.d1lta.prison;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Vova implements CommandExecutor {

    List<ItemStack> a = List.of(
            new ItemStack(Material.STONE, 64),
            new ItemStack(Material.STONE, 64),
            new ItemStack(Material.STONE, 64),
            new ItemStack(Material.STONE, 64)
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }
}
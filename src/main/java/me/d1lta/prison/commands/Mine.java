package me.d1lta.prison.commands;

import me.d1lta.prison.enums.Mines;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Mine implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            openUI(new LittlePlayer(((Player) sender).getUniqueId()));
            return true;
        }
        return false;
    }

    public static boolean isAllowed(ItemStack stack, LittlePlayer pl) {
        return NBT.getIntNBT(stack, "lvl") <= pl.getLevel();
    }

    private static void openUI(LittlePlayer pl) {
        Inventory inventory = Bukkit.createInventory(null, 27, CValues.get("Шахты", 100, 100, 100).create());
        for (Mines mine: Mines.values()) {
            ItemStack stack = new ItemStack(mine.material);
            ItemMeta meta = stack.getItemMeta();
            meta.lore(mine.getLoreComponents());
            stack.setItemMeta(meta);
            stack = NBT.addNBT(stack, "nbt", mine.world);
            stack = NBT.addNBT(stack, "lvl", mine.lvl);
            if (!mine.equals(Mines.VAULT)) {
                inventory.addItem(stack);
            } else {
                inventory.setItem(26, stack);
            }
        }
        pl.openInventory(inventory);
    }
}

package me.d1lta.prison.commands;

import java.util.List;
import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Blockstats implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            openUI(new LittlePlayer(((Player) sender).getUniqueId()));
            return true;
        }
        return false;
    }

    private static void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 54, CValues.get("Статистика", 100, 100, 100).create());
        AllowedBlocks.blockMats.forEach(it -> {
            ItemMeta meta = AllowedBlocks.getBlock(it).getItemMeta();
            meta.displayName(CValues.get(meta.displayName(), 255, 255, 255).create());
            meta.lore(List.of(CValues.get(String.format("Всего вскопано %d блоков", pl.getBlocks(it.name())), 170, 170, 170).create()));
            ItemStack stack = new ItemStack(it);
            stack.setItemMeta(meta);
            inv.addItem(stack);
        });
        pl.openInventory(inv);
    }
}

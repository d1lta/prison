package me.d1lta.prison.commands;

import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnchantmentList implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            int lvl = 3;
            int chance = 100;
            if (args.length == 2) {
                lvl = Integer.parseInt(args[0]);
                chance = Integer.parseInt(args[1]);
            }
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            Inventory inventory = Bukkit.createInventory(null, 54, CValues.get("Древние зачарования", 255, 255, 0).create());
            for (Enchantments ench : Enchantments.values()) {
                if (ench.equals(Enchantments.NULL)) { continue; }
                inventory.addItem(ench.getBook(lvl, chance).getBook());
            }
            pl.openInventory(inventory);

        }
        return true;
    }

}

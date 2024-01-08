package me.d1lta.prison.commands;

import me.d1lta.prison.items.Armor;
import me.d1lta.prison.items.Tool;
import me.d1lta.prison.items.Weapon;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (args.length == 1) {
                switch (args[0]) {
                    case "shovel" -> pl.getInventory().addItem(Tool.shovel());
                    case "pickaxe" -> pl.getInventory().addItem(Tool.pickaxe());
                    case "rarepickaxe" -> pl.getInventory().addItem(Tool.rare_pickaxe());
                    case "axe" -> pl.getInventory().addItem(Tool.axe());
                    case "shears" -> pl.getInventory().addItem(Tool.shears());

                    case "helmet" -> pl.getInventory().addItem(Armor.helmet());
                    case "chestplate" -> pl.getInventory().addItem(Armor.chestplate());
                    case "leggings" -> pl.getInventory().addItem(Armor.leggings());
                    case "boots" -> pl.getInventory().addItem(Armor.boots());

                    case "bow" -> pl.getInventory().addItem(Weapon.bow());
                    case "sword" -> pl.getInventory().addItem(Weapon.sword());
                }
            }
        }
        return false;
    }
}

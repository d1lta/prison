package me.d1lta.prison.commands;

import java.util.List;
import me.d1lta.prison.items.Armor;
import me.d1lta.prison.items.BrokenElderKey;
import me.d1lta.prison.items.Chicken;
import me.d1lta.prison.items.ElderDust;
import me.d1lta.prison.items.ElderKey;
import me.d1lta.prison.items.ElderStar;
import me.d1lta.prison.items.Key;
import me.d1lta.prison.items.ToiletPaper;
import me.d1lta.prison.items.Tool;
import me.d1lta.prison.items.VaultAccess;
import me.d1lta.prison.items.Weapon;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class GiveItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (args.length == 0) {
                openInv(pl);
                return true;
            }
            if (args.length == 2) {
                if (args[0].equals("dust")) {
                    pl.getInventory().addItem(ElderDust.getDust(Integer.parseInt(args[1])));
                }
                return true;
            }
        }
        return false;
    }

    private void openInv(LittlePlayer pl) {
        Inventory inventory = Bukkit.createInventory(null, 27, CValues.get("Предметы", 100, 100, 100).create());
        List.of(Tool.shovel(), Tool.pickaxe(), Tool.rare_pickaxe(), Tool.axe(), Tool.shears(),
                Armor.helmet(), Armor.chestplate(), Armor.leggings(), Armor.boots(), Weapon.bow(), Weapon.sword(),
                VaultAccess.getAccess(), ToiletPaper.getPaper(), Key.getKey(), ElderStar.getStar(), ElderKey.getKey(),
                BrokenElderKey.getKey(), ElderDust.getDust(100), Chicken.getChicken()).forEach(inventory::addItem);
        pl.openInventory(inventory);
    }

}

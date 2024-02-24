package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.LevelBoosts;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Level implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            openUI(pl, pl.getLevel());
            return true;
        }
        return false;
    }

    public static void levelUp(LittlePlayer pl) {
        int moneyreq = 0;
        int lvl = pl.getLevel();
        List<String> requirements = Main.config.getConfig().getStringList("levels.level_" + (lvl + 1) + ".requirements");
        String[] parts;
        for (String it : requirements) {
            parts = it.split(":");
            if (parts[0].equalsIgnoreCase("money")) {
                if (pl.getMoney() < Integer.parseInt(parts[1])) {
                    pl.sendMessage(CValues.get("Недостаточно денег!", 255, 100, 0).create());
                    pl.closeInventory();
                    return;
                } else {
                    moneyreq = Integer.parseInt(parts[1]);
                }
            } else {
                if (pl.getBlocks() < Integer.parseInt(parts[1])) {
                    pl.sendMessage(CValues.get("Недостаточно блоков!", 255, 100, 0).create());
                    pl.closeInventory();
                    return;
                }
            }
        }
        pl.removeMoney(moneyreq);
        pl.lvlUp();
        LevelBoosts.get(pl.getLevel()).applyToPlayer(pl);
        pl.sendMessage("lvlUP!");
        pl.closeInventory();
    }

    private void openUI(LittlePlayer pl, int lvl) {
        Inventory UI = Bukkit.createInventory(null, InventoryType.HOPPER, CValues.get("Уровень", 100, 100, 100).create());
        List<String> requirements = Main.config.getConfig().getStringList("levels.level_" + (lvl + 1) + ".requirements");
        String[] parts;
        List<Component> lore = new ArrayList<>();
        for (String it: requirements) {
            parts = it.split(":");
            TextColor color = makeColor(pl, parts[0].toLowerCase(), Integer.parseInt(parts[1]));
            lore.add(CValues.get(translate(parts[0]), color).create().append(CValues.get(parts[1], color).create()));
        }
        ItemStack lvlup = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = lvlup.getItemMeta();
        meta.lore(lore);
        meta.displayName(CValues.get("Повысить уровень", 255, 200 ,0).create());
        lvlup.setItemMeta(meta);
        lvlup = NBT.addNBT(lvlup, "type", "lvlup");
        UI.setItem(2, lvlup);
        pl.openInventory(UI);
    }

    private TextColor makeColor(LittlePlayer pl, String type, int req) {
        if (type.equalsIgnoreCase("money")){
            if (pl.getMoney() < req) {
                return TextColor.color(255, 0, 0);
            } else {
                return TextColor.color(0, 255, 0);
            }
        } else {
            if (pl.getBlocks() < req) {
                return TextColor.color(255, 0, 0);
            } else {
                return TextColor.color(0, 255, 0);
            }
        }

    }

    private String translate(String s) {
        return switch (s.toLowerCase()) {
            case "blocks" -> "Блоки: ";
            case "money" -> "Денег: ";
            default -> "ERR";
        };
    }

}

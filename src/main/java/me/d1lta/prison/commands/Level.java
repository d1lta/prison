package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Level implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            openUI(pl, Integer.parseInt(Jedis.get(pl.getUniqueId() + ".lvl")));
        }
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getType().equals(InventoryType.HOPPER)) {
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }
            if (NBT.getStringNBT(e.getCurrentItem(), "type").equalsIgnoreCase("lvlup")) {
                levelUp((Player) e.getWhoClicked());
            }
        }
    }

    private boolean levelUp(Player pl) {
        int moneyreq = 0;
        int lvl = Integer.parseInt(Jedis.get(pl.getUniqueId() + ".lvl"));
        List<String> requirements = Main.config.getConfig().getStringList("levels.level_" + (lvl + 1) + ".requirements");
        String[] parts;
        for (String it : requirements) {
            parts = it.split(":");
            if (parts[0].equalsIgnoreCase("money")) {
                if (Double.parseDouble(Jedis.get(pl.getUniqueId() + "." + parts[0].toLowerCase())) < Integer.parseInt(parts[1])) {
                    pl.sendMessage("Требования не выполнены");
                    pl.closeInventory();
                    return false;
                } else {
                    moneyreq = Integer.parseInt(parts[1]);
                }
            } else {
                if (Integer.parseInt(Jedis.get(pl.getUniqueId() + "." + parts[0].toLowerCase())) < Integer.parseInt(parts[1])) {
                    pl.sendMessage("Требования не выполнены");
                    pl.closeInventory();
                    return false;
                }
            }
        }
        Jedis.set(pl.getUniqueId() + ".money", String.valueOf(Double.parseDouble(Jedis.get(pl.getUniqueId() + ".money")) - moneyreq));

        Jedis.set(pl.getUniqueId() + ".lvl", String.valueOf((Integer.parseInt(Jedis.get(pl.getUniqueId() + ".lvl")) + 1)));
        pl.sendMessage("lvlUP!");
        pl.closeInventory();
        return true;
    }

    private void openUI(Player pl, int lvl) {
        Inventory UI = Bukkit.createInventory(null, InventoryType.HOPPER, "Уровень");
        List<String> requirements = Main.config.getConfig().getStringList("levels.level_" + (lvl + 1) + ".requirements");
        String[] parts;
        List<Component> lore = new ArrayList<>();
        for (String it: requirements) {
            parts = it.split(":");
            lore.add(Component.text(makeColor(translate(parts[0]) + ": " + parts[1], pl, parts[0].toLowerCase(), Integer.parseInt(parts[1]))));
        }
        ItemStack lvlup = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = lvlup.getItemMeta();
        meta.lore(lore);
        meta.displayName(Component.text(ChatColor.GOLD + "Повысить уровень"));
        lvlup.setItemMeta(meta);
        lvlup = NBT.addNBT(lvlup, "type", "lvlup");
        UI.setItem(2, lvlup);
        pl.openInventory(UI);
    }

    private String makeColor(String s, Player pl, String type, int req) {
        if (type.equalsIgnoreCase("money")){
            if (Double.parseDouble(Jedis.get(pl.getUniqueId() + "." + type)) < req) {
                return ChatColor.RED + s;
            } else {
                return ChatColor.GREEN + s;
            }
        } else {
            if (Integer.parseInt(Jedis.get(pl.getUniqueId() + "." + type)) < req) {
                return ChatColor.RED + s;
            } else {
                return ChatColor.GREEN + s;
            }
        }

    }

    private String translate(String s) {
        return switch (s.toLowerCase()) {
            case "blocks" -> "Блоки";
            case "money" -> "Денег";
            default -> "ERR";
        };
    }

}

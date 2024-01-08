package me.d1lta.prison.commands;

import me.d1lta.prison.Main;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
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
import org.jetbrains.annotations.NotNull;

public class Upgrades implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (pl.getItemInMainHand() == null) {
                return false;
            }
            if (NBT.getStringNBT(pl.getItemInMainHand(), "type") != null) {
                openUI(pl, NBT.getStringNBT(pl.getItemInMainHand(), "type"));
            }
            return true;
        }
        return false;
    }

    private static void openUI(LittlePlayer player, String type) {
        Inventory inventory = Bukkit.createInventory(null, 54, ComponentUtils.component("Список улучшений."));
        for (int i = 1; i <= Main.config.getConfig().getConfigurationSection("upgrades." + type).getKeys(false).size(); i++) {
            inventory.addItem(Upgrade.getPrisonItem(player, type, i, true));
        }
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().title().equals(ComponentUtils.component("Список улучшений."))) {
            e.setCancelled(true);
        }
    }
}
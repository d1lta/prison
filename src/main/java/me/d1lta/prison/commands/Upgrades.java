package me.d1lta.prison.commands;

import java.util.Map;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class Upgrades implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (!CheckUtils.checkForNull(pl.getItemInMainHand())) { return false; }
            String type = NBT.getStringNBT(pl.getItemInMainHand(), "type");
            if (type != null && !type.isEmpty()) {
                openUI(pl, NBT.getStringNBT(pl.getItemInMainHand(), "type"));
            }
            return true;
        }
        return false;
    }

    private static void openUI(LittlePlayer player, String type) {
        Inventory inventory = Bukkit.createInventory(null, 54, DComponent.create("Список улучшений."));
        for (int i = 1; i <= Main.config.getConfig().getConfigurationSection("upgrades." + type).getKeys(false).size(); i++) {
            inventory.addItem(Upgrade.getPrisonItem(player, type, i, true, Map.of()));
        }
        player.openInventory(inventory);
    }

}

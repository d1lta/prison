package me.d1lta.prison.commands;

import java.util.List;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class Blockstats implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            openUI(new LittlePlayer(((Player) sender).getUniqueId()));
        }
        return false;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Статистика") && e.getCurrentItem() != null) {
            e.setCancelled(true);
        }
    }

    private static void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 54, "Статистика");
        AllowedBlocks.blocks.forEach(it -> {
            ItemMeta meta = it.getItemMeta();
            meta.displayName(meta.displayName().color(TextColor.color(255, 255, 255)));
            meta.lore(
                    List.of(Component.text("Всего вскопано ").color(TextColor.color(170,170,170))
                    .append(Component.text(Jedis.get(pl.uuid + ".blocks." + it.getType().name().toLowerCase())).color(TextColor.color(255,255,255))
                    .append(Component.text(" блоков").color(TextColor.color(170,170,170))))));
            it.setItemMeta(meta);
            inv.addItem(it);
        });
        pl.openInventory(inv);
    }
}

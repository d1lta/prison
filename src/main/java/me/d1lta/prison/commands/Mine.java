package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.Teleport;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.LocationUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Mine implements CommandExecutor, Listener {

    Component title = ComponentUtils.component("Шахты");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            openUI(new LittlePlayer(((Player) sender).getUniqueId()));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().title().equals(title)) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null) {
                LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
                if (isAllowed(e.getCurrentItem(), pl)) {
                    String nbt = NBT.getStringNBT(e.getCurrentItem(), "nbt");
                    if (nbt.equals("vault")) {
                        if (!pl.hasVaultAccess()) {
                            pl.sendMessage("У вас нет доступа!");
                            pl.closeInventory();
                            return;
                        }
                    }
                    e.getWhoClicked().closeInventory();
                    Teleport.tp(pl, pl.getLocation(), LocationUtils.spawnPoint(NBT.getStringNBT(e.getCurrentItem(), "nbt")));
                } else {
                    pl.closeInventory();
                    pl.sendMessage("вы слишком малого лвла");
                }
            }
        }
    }

    private boolean isAllowed(ItemStack stack, LittlePlayer pl) {
        return Main.config.getConfig().getInt("minelore." + NBT.getStringNBT(stack, "nbt") + ".reqlvl") <= pl.getLevel();
    }

    private void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 27, title);
        inv = fillMines(inv);
        pl.openInventory(inv);
    }

    private Inventory fillMines(Inventory inventory) {
        for (String it : Main.config.getConfig().getConfigurationSection("minelore").getKeys(false)) {
            ItemStack stack = new ItemStack(Objects.requireNonNull(Material.getMaterial(String.valueOf(Main.config.getConfig().get("minelore." + it + ".M")))));
            ItemMeta meta = stack.getItemMeta();
            List<Component> list = new ArrayList<>();
            for (String s : Main.config.getConfig().getStringList("minelore." + it + ".lore")) {
                list.add(Component.text(s));
            }
            meta.lore(list);
            stack.setItemMeta(meta);
            String nbt = Main.config.getConfig().getString("minelore." + it + ".nbt");
            stack = NBT.addNBT(stack, "nbt", nbt);
            stack = NBT.addNBT(stack, "lvl", Main.config.getConfig().getString("minelore." + it + ".reqlvl"));
            if (nbt.equals("vault")) {
                inventory.setItem(26, stack);
            } else {
                inventory.addItem(stack);
            }
        }
        return inventory;
    }
}

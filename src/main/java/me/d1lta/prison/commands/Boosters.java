package me.d1lta.prison.commands;

import java.util.List;
import java.util.Objects;
import me.d1lta.prison.boosters.BlockBoosts;
import me.d1lta.prison.boosters.Boost;
import me.d1lta.prison.boosters.BoosterHandler;
import me.d1lta.prison.utils.DComponent;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Boosters implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            switch (args.length) {
                case 0: {
                    openUI(pl);
                    return true;
                }
                case 5: { //boosters add d1lta blocks 2.0 false || /boosters add <name> <boosttype> <multiplier> <local> (true\false)
                    if (args[0].equals("add") && pl.isOp()) {
                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                            BoosterHandler.addBoost(new LittlePlayer(Bukkit.getPlayer(args[1]).getUniqueId()), args[2], args[3], 1, Boolean.parseBoolean(args[4]));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 54, DComponent.create("Бустеры"));
        List<Boost> b = BoosterHandler.getBoosts(pl);
        for (int i = 0; i < b.size(); i++) {
            ItemStack stack = new ItemStack(b.get(i).getMat());
            stack = Boost.setMeta(stack, b.get(i));
            inv.setItem(i, stack);
        }
        pl.openInventory(inv);
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().title().equals(DComponent.create("Бустеры"))) {
            LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            ItemStack stack = e.getCurrentItem();
            if (Objects.equals(NBT.getStringNBT(stack, "type"), "money")) {
                pl.sendMessage("не реализовано.");
                pl.closeInventory();
                return;
            }
            if (Objects.equals(NBT.getStringNBT(stack, "type"), "blocks")) {
                if (Objects.equals(NBT.getStringNBT(stack, "local"), "true")) {
                    BlockBoosts.localBoost(pl, Double.parseDouble(NBT.getStringNBT(stack, "multiplier")));
                    pl.closeInventory();
                    return;
                }
                if (Objects.equals(NBT.getStringNBT(stack, "local"), "false")) {
                    BlockBoosts.globalBoost(new Boost(false, "global",  Double.parseDouble(NBT.getStringNBT(stack, "multiplier")), 0));
                    pl.closeInventory();
                    return;
                }
            }
        }
    }
}

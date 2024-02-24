package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Faction implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            openUI(pl);
            return true;
        }
        return false;
    }

    private void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, CValues.get("Выбор фракции", 100, 100, 100).create());
        for (ItemStack stack: getFactions(pl)) { inv.addItem(stack); }
        pl.openInventory(inv);

    }

    private static List<ItemStack> getFactions(LittlePlayer pl) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Factions faction: Factions.values()) {
            if (faction.equals(Factions.NO_FACTION)) { continue; }
            ItemStack stack = new ItemStack(faction.getMaterial());
            ItemMeta meta = stack.getItemMeta();
            meta.displayName(faction.getComponent());
            meta.lore(getLore(pl, faction));
            stack.setItemMeta(meta);
            stack = NBT.addNBT(stack, "f", faction.getName());
            stacks.add(stack);
        }
        return stacks;
    }

    private static List<Component> getLore(LittlePlayer pl, Factions faction) {
        List<Component> lore = new ArrayList<>();
        if (pl.getFaction().equals(faction)) {
            lore.add(CValues.get("Нажмите", 255, 255, 255).create()
                    .append(CValues.get(" ЛКМ ", 255, 170, 0).create())
                    .append(CValues.get("чтобы выйти из фракции", 255, 255, 255).create()));
            lore.add(CValues.get("Стоимость: ", 255, 255, 255).create()
                    .append(CValues.get("10.000$", 255, 170, 0).create()));
        } else {
            if (pl.getFaction().equals(Factions.NO_FACTION)) {
                lore.add(CValues.get("Нажмите", 255, 255, 255).create()
                        .append(CValues.get(" ЛКМ ", 255, 170, 0).create())
                        .append(CValues.get("чтобы вступить в фракцию", 255, 255, 255).create()));
            } else {
                lore.add(CValues.get("Нажмите", 255, 255, 255).create()
                        .append(CValues.get(" ЛКМ ", 255, 170, 0).create())
                        .append(CValues.get("чтобы сменить фракцию", 255, 255, 255).create()));
                lore.add(CValues.get("Стоимость: ", 255, 255, 255).create()
                        .append(CValues.get("10.000$", 255, 170, 0).create()));
            }
        }
        return lore;
    }
}

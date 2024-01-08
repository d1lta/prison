package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Faction implements CommandExecutor, Listener {

    Component title = ComponentUtils.component("Выбор фракции");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            openUI(pl);
        }
        return false;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().title().equals(title)) {
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            e.setCancelled(true);
            LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
            Factions chosen = Factions.getFaction(NBT.getStringNBT(e.getCurrentItem(), "f"));
            if (Objects.equals(chosen, getPlayerFaction(pl))) { // leave from faction
                changeFraction(pl, Factions.NO_FACTION, true);
            } else if (getPlayerFaction(pl).equals(Factions.NO_FACTION)) { // no faction -> to faction
                changeFraction(pl, chosen, false);
            } else if (!getPlayerFaction(pl).equals(Factions.NO_FACTION) && !getPlayerFaction(pl).equals(chosen)) { // change faction
                changeFraction(pl, chosen, true);
            }
            pl.closeInventory();
        }
    }

    private static void changeFraction(LittlePlayer pl, Factions faction, boolean money) {
        if (money) {
            if (pl.getMoney() >= 10000.00) {
                pl.removeMoney(10000.00);
                Jedis.set(pl.uuid + ".faction", faction.getName());
                pl.sendMessage("change faction");
            } else {
                pl.sendMessage("no money");
            }
        } else {
            Jedis.set(pl.uuid + ".faction", faction.getName());
            pl.sendMessage("no faction -> to faction");
        }
    }

    private void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, title);
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
        boolean isPlayerFaction = isPlayerFaction(pl, faction);
        List<Component> lore = new ArrayList<>();
        if (isPlayerFaction) {
            lore.add(ComponentUtils.component("Нажмите ЛКМ чтобы выйти из фракции"));
            lore.add(ComponentUtils.component("Стоимость: 10000$"));
        } else {
            if (isPlayerNoFaction(pl)) {
                lore.add(ComponentUtils.component("Нажмите ЛКМ чтобы вступить в фракцию"));
            } else {
                lore.add(ComponentUtils.component("Нажмите ЛКМ чтобы сменить фракцию"));
                lore.add(ComponentUtils.component("Стоимость: 10000$"));
            }
        }
        return lore;
    }

    private static Factions getPlayerFaction(LittlePlayer pl) {
        return pl.getFaction();
    }

    private static boolean isPlayerNoFaction(LittlePlayer pl) {
        return pl.getFaction().getName().equals("Без фракции");
    }

    private static boolean isPlayerFaction(LittlePlayer pl, Factions faction) {
        return pl.getFaction().getName().equals(faction.getName());

    }
}

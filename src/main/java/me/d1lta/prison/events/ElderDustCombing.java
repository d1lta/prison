package me.d1lta.prison.events;

import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.items.ElderDust;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ElderDustCombing implements Listener {

    @EventHandler
    public void DustCombing(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        } else if (e.getCursor().getType() == Material.AIR) {
            return;
        }

        if (NBT.getStringNBT(e.getCursor(), "type").equals("elder_dust")) {
            if (check(e.getCursor(), e.getCurrentItem())) { return; }
        } else {
            return;
        }

        int chance = chance(e.getCursor(), e.getCurrentItem());

        if (chance >= 100) { chance = 100;}

        if (NBT.getStringNBT(e.getCurrentItem(), "type").equals("elder_dust")) { // combing two dusts
            e.getWhoClicked().getInventory().setItem(e.getSlot(), ElderDust.getDust(chance));
        } else if (EnchantmentBook.isEnchantingBook(e.getCurrentItem())) { // combing dust/book
            e.getWhoClicked().getInventory().setItem(e.getSlot(), EnchantmentBook.replaceChanceLore(NBT.replaceNBT(e.getCurrentItem(), "chance", String.valueOf(chance)), chance));
        } else {
            return;
        }
        e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
        e.setCancelled(true);
    }

    public int chance(ItemStack cursor, ItemStack current) {
        return (Integer.parseInt(NBT.getStringNBT(current, "chance")) + Integer.parseInt(NBT.getStringNBT(cursor, "chance")));
    }

    public boolean check(ItemStack cursor, ItemStack current) {
        if (!NBT.getKeys(current).contains("chance") || !NBT.getKeys(cursor).contains("chance")) { return true; } // TODO: int check
        if (Integer.parseInt(NBT.getStringNBT(current, "chance")) == 100) { return true; }
        if (Integer.parseInt(NBT.getStringNBT(cursor, "chance")) == 100) { return true; }
        if (cursor.getAmount() > 1 || current.getAmount() > 1) { return true; }
        return false;
    }

}

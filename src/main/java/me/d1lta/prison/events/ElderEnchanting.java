package me.d1lta.prison.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderEnchanting implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        } else if (e.getCursor().getType() == Material.AIR) {
            return;
        }
        if (EnchantmentBook.isEnchantingBook(e.getCursor())) {
            LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
            Set<String> keys = NBT.getKeys(e.getCursor());
            for (Enchantments it : Enchantments.values()) {
                if (keys.contains(it.getName())) {
                    if (!it.getApplicableTools().contains(e.getCurrentItem().getType())) { return; }
                    if (NBT.getKeys(e.getCurrentItem()).contains(it.getName())) { return;  }
                    if (Integer.parseInt(NBT.getStringNBT(e.getCursor(), "chance")) <= new Random().nextInt(1, 101)) {
                        e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                        e.setCancelled(true);
                        return;
                    }
                    ItemStack stack = NBT.addNBT(e.getCurrentItem(), it.getName(), NBT.getIntNBT(e.getCursor(), it.getName()));
                    ItemMeta meta = stack.getItemMeta();
                    meta.lore(lore(stack));
                    stack.setItemMeta(meta);
                    pl.getInventory().setItem(e.getSlot(), stack);
                    e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                    e.setCancelled(true);
                }
            }
        }
    }

    private List<net.kyori.adventure.text.Component> lore(ItemStack stack) {
        Map<String, Integer> enchants = new HashMap<>();
        List<net.kyori.adventure.text.Component> lore = new ArrayList<>();
        for (String it : NBT.getKeys(stack)) {
            for (Enchantments e: Enchantments.values()) {
                if (e.getName().equals(it)) {
                    enchants.put(e.getName(), NBT.getIntNBT(stack, e.getName()));
                }
            }
        }
        if (enchants.size() > 0) {
            lore.add(DComponent.create(""));
            lore.add(DComponent.create("Древние зачарования:").color(
                    TextColor.color(176, 0, 190)));
            enchants.forEach((k,v) -> lore.add(
                    Enchantments.getEnchantment(k).getColoredNameWithLevel(v)));
        }
        lore.add(DComponent.create(""));
        lore.add(DComponent.create("Уровень предмета >> ").append(
                DComponent.create(String.valueOf(NBT.getIntNBT(stack, "level")), TextColor.color(250, 249, 86))));
        return lore;
    }
}

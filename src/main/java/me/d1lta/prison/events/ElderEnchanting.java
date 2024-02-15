package me.d1lta.prison.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.d1lta.prison.enchants.EnchTranslate;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.enchants.enchantments.Hammer;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import me.d1lta.prison.utils.RomanNumeration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
            if (keys.contains("hammer")) {
                if (Hammer.applicableTo.contains(e.getCurrentItem().getType())) {
                    if (NBT.getKeys(e.getCurrentItem()).contains("hammer")) {
                        return;
                    }
                    pl.getInventory().setItem(e.getSlot(), NBT.addNBT(e.getCurrentItem(), "hammer", NBT.getIntNBT(e.getCurrentItem(), "hammer")));
                    e.getCursor().setType(Material.AIR);
                    e.setCancelled(true);
                }
            }
        }
    }

    private List<Component> lore(ItemStack stack) {
        Map<String, Integer> enchants = new HashMap<>();
        stack.get

        if (enchants != null && enchants.size() > 0) {
            components.add(ComponentUtils.component(""));
            components.add(ComponentUtils.component("Древние зачарования:").color(
                    TextColor.color(176, 0, 190)));
            enchants.forEach((k,v) -> components.add(
                    EnchTranslate.translateWithColor(k).append(ComponentUtils.component(" " + RomanNumeration.get(v), EnchTranslate.getColor(k)))));
        }
        components.add(ComponentUtils.component(""));
        components.add(ComponentUtils.component("Уровень предмета >> ").append(ComponentUtils.component(String.valueOf(lvl), TextColor.color(250, 249, 86))));
    }
}

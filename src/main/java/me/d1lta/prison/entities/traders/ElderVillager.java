package me.d1lta.prison.entities.traders;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.entities.PrisonTrader;
import me.d1lta.prison.items.BrokenElderKey;
import me.d1lta.prison.items.ElderKey;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ElderVillager extends PrisonTrader {

    public static Component name = DComponent.create("Древний торговец", TextColor.color(214, 144, 0));
    public static Component title = DComponent.create("Древний торговец", TextColor.color(46, 46, 46));

    public ElderVillager(Location location) {
        super(location, name, 999, false);
    }

    public static void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, title);
        inv.setItem(1, setPrice(BrokenElderKey.getKey(), "broken_elder", 10));
        inv.setItem(3, setPrice(ElderKey.getKey(), "elder", 60));
        pl.openInventory(inv);
    }

    private static ItemStack setPrice(ItemStack stack, String type, int price) {
        ItemMeta meta = stack.getItemMeta();
        List<Component> lore;
        if (meta.lore() == null) { lore = new ArrayList<>(); } else { lore = meta.lore(); } // Объявление lore
        assert lore != null;
        lore.add(DComponent.create("Цена")
                .append(DComponent.create(" >> ", TextColor.color(129, 129, 122)))
                .append(DComponent.create(price + "", TextColor.color(214, 144, 0)))
                .append(DComponent.create(" древних звезд", TextColor.color(255, 172, 0))));
        meta.lore(lore);
        stack.setItemMeta(meta);
        stack = NBT.addNBT(stack, "price", price);
        stack = NBT.addNBT(stack, "type", type);
        return stack;
    }
}

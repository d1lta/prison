package me.d1lta.prison.entities.traders;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.entities.PrisonTrader;
import me.d1lta.prison.items.Apple;
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

public class StartVillager extends PrisonTrader {

    public static Component name = DComponent.create("Торговец", TextColor.color(214, 144, 0));
    public static Component title = DComponent.create("Торговец", TextColor.color(46, 46, 46));

    public StartVillager(Location location) {
        super(location, name, 999, false);
    }

    public static void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, title);
        inv.setItem(1, setPrice(Upgrade.getPrisonItem(null, "pickaxe", 1, false, null), 30, "pickaxe"));
        inv.setItem(2, setPrice(Upgrade.getPrisonItem(null, "shovel", 1, false, null), 8, "shovel"));
        inv.setItem(3, setPrice(Apple.getApple(), 1, "apple"));
        pl.openInventory(inv);
    }

    private static ItemStack setPrice(ItemStack stack, int price, String type) {
        ItemMeta meta = stack.getItemMeta();
        List<Component> lore;
        if (meta.lore() == null) { lore = new ArrayList<>(); } else { lore = meta.lore(); }
        assert lore != null;
        lore.add(DComponent.create("Цена")
                .append(DComponent.create(" >> ", TextColor.color(129, 129, 122)))
                .append(DComponent.create(price + ".0", TextColor.color(214, 144, 0)))
                .append(DComponent.create("$", TextColor.color(214, 144, 0))));
        meta.lore(lore);
        stack.setItemMeta(meta);
        stack = NBT.addNBT(stack, "price", price);
        stack = NBT.addNBT(stack, "type", type);
        return stack;
    }

}

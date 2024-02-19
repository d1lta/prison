package me.d1lta.prison.mobs.traders;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.items.Apple;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StartVillager implements Listener {

    public static net.kyori.adventure.text.Component traderName = DComponent.create("Торгаш", TextColor.color(214, 144, 0));
    public static net.kyori.adventure.text.Component traderTitle = DComponent.create("Торгаш", TextColor.color(46, 46, 46));

    public StartVillager(Location location) {
        if (location == null) { return; }
        Villager trader = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        trader.customName(traderName);
        trader.setCustomNameVisible(true);

        trader.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(999);
        trader.setHealth(999);
        trader.setAI(false);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Villager) {
            if (e.getRightClicked().name().equals(traderName)) {
                LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
                openInv(pl);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e) {
        if (e.getView().title().equals(traderTitle)) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getAmount() != 0) {
                ItemStack stack = e.getCurrentItem();
                int price = NBT.getIntNBT(stack, "price");
                if (price == 0) { return; }
                LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
                if (pl.getMoney() >= price) {
                    pl.removeMoney(price);
                    switch (NBT.getStringNBT(stack, "type")) {
                        case "pickaxe" -> pl.giveItem(Upgrade.getPrisonItem(null, "pickaxe", 1, false, null));
                        case "shovel" -> pl.giveItem(Upgrade.getPrisonItem(null, "shovel", 1, false, null));
                        case "apple" -> pl.giveItem(Apple.getApple());
                    }
                }
            }
        }
    }

    private void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, traderTitle);
        inv.setItem(1, setPrice(Upgrade.getPrisonItem(null, "pickaxe", 1, false, null), 30, "pickaxe"));
        inv.setItem(2, setPrice(Upgrade.getPrisonItem(null, "shovel", 1, false, null), 8, "shovel"));

        inv.setItem(3, setPrice(Apple.getApple(), 1, "apple"));
        pl.openInventory(inv);
    }

    private ItemStack setPrice(ItemStack stack, int price, String type) {
        ItemMeta meta = stack.getItemMeta();
        List<net.kyori.adventure.text.Component> lore;
        if (meta.lore() == null) {
            lore = new ArrayList<>();
        } else {
            lore = meta.lore();
        }
        List<net.kyori.adventure.text.Component> list = new ArrayList<>();
        list.add(DComponent.create("Цена")
                .append(DComponent.create(" >> ", TextColor.color(129, 129, 122)))
                .append(DComponent.create(price + ".0", TextColor.color(214, 144, 0)))
                .append(DComponent.create("$", TextColor.color(214, 144, 0))));
        lore.addAll(list);
        meta.lore(lore);
        stack.setItemMeta(meta);
        stack = NBT.addNBT(stack, "price", price);
        stack = NBT.addNBT(stack, "type", type);
        return stack;
    }

    @EventHandler
    public void onVillagerHurt(EntityDamageEvent e) {
        if (e.getEntity().name().equals(traderName)) {
            e.setCancelled(true);
        }
    }

}

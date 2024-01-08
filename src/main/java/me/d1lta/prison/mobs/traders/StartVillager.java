package me.d1lta.prison.mobs.traders;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.items.Apple;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StartVillager implements Listener {

    public static Component traderName = ComponentUtils.component("Торгаш", TextColor.color(214, 144, 0));
    public static Component traderTitle = ComponentUtils.component("Торгаш", TextColor.color(46, 46, 46));

    public StartVillager(Location location) {
        if (location == null) { return; }
        Villager trader = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        trader.customName(traderName);
        trader.setCustomNameVisible(true);

        AttributeInstance attribute = trader.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(999);
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
                        case "pickaxe" -> pl.giveItem(Upgrade.getPrisonItem(null, "pickaxe", 1, false));
                        case "shovel" -> pl.giveItem(Upgrade.getPrisonItem(null, "shovel", 1, false));
                        case "apple" -> pl.giveItem(Apple.getApple());
                    }
                }
            }
        }
    }

    private void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, traderTitle);
        inv.setItem(1, setPrice(Upgrade.getPrisonItem(null, "pickaxe", 1, false), 30, "pickaxe"));
        inv.setItem(2, setPrice(Upgrade.getPrisonItem(null, "shovel", 1, false), 8, "shovel"));

        inv.setItem(3, setPrice(Apple.getApple(), 1, "apple"));
        pl.openInventory(inv);
    }

    private ItemStack setPrice(ItemStack stack, int price, String type) {
        ItemMeta meta = stack.getItemMeta();
        List<Component> lore;
        if (meta.lore() == null) {
            lore = new ArrayList<>();
        } else {
            lore = meta.lore();
        }
        List<Component> list = new ArrayList<>();
        list.add(ComponentUtils.component("Цена")
                .append(ComponentUtils.component(" >> ", TextColor.color(129, 129, 122)))
                .append(ComponentUtils.component(price + ".0", TextColor.color(214, 144, 0)))
                .append(ComponentUtils.component("$", TextColor.color(214, 144, 0))));
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

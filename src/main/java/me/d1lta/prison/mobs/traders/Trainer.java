package me.d1lta.prison.mobs.traders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Trainer implements Listener {

    public static Component traderName = DComponent.create("Тренер", TextColor.color(0, 88, 156));
    public static Component traderTitle = DComponent.create("Тренер", TextColor.color(0, 88, 156));

    public Trainer(Location location) {
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
            if (CheckUtils.checkForNull(e.getCurrentItem())) {
                if (!NBT.getKeys(e.getCurrentItem()).contains("trainer")) { return; }
                LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
                for (String it : NBT.getKeys(e.getCurrentItem())) {
                    if (it.equalsIgnoreCase("money")) {
                        if (pl.getMoney() >= Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "money"))) {
                            pl.removeMoney(NBT.getIntNBT(e.getCurrentItem(), "money"));
                        } else {
                            pl.sendMessage(DComponent.create("Недостаточно средств"));
                            pl.closeInventory();
                            return;
                        }
                    } else if (it.equalsIgnoreCase("zombie")) {
                        if (pl.getZombies() < Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "zombie"))) {
                            pl.sendMessage(DComponent.create("Недостаточно зомби"));
                            pl.closeInventory();
                            return;
                        }
                    } else if (it.equalsIgnoreCase("bat")) {
                        if (pl.getZombies() < Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "bat"))) {
                            pl.sendMessage(DComponent.create("Недостаточно мышек"));
                            pl.closeInventory();
                            return;
                        }
                    }
                }
                addLevel(pl, NBT.getStringNBT(e.getCurrentItem(), "trainer"));
                pl.closeInventory();
                openInv(pl);
            }
        }
    }

    private void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, traderTitle);
        inv.setItem(1, getItem("strength", pl));
        inv.setItem(2, getItem("agility", pl));
        inv.setItem(3, getItem("needs", pl));
        pl.openInventory(inv);
    }

    private ItemStack getItem(String type, LittlePlayer pl) {
        ItemStack stack = new ItemStack(getMat(type));
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(getName(type, getLevel(pl, type) + 1));
        ArrayList<Component> components = new ArrayList<>();
        Map<String, String> nbts = new HashMap<>();
        components.add(DComponent.create(""));
        components.addAll(getLore(type));
        components.add(DComponent.create(""));
        components.add(DComponent.create("Требования: ", TextColor.color(255, 255, 0)));
        String[] parts;
        for (String it : Main.config.getConfig().getStringList("trainer." + type + ".level_" + (getLevel(pl, type) + 1))) {
            parts = it.split(":");
            if (parts[0].equalsIgnoreCase("money")) {
                components.add(DComponent.create("- Деньги: ", TextColor.color(255, 172, 0)).append(
                                DComponent.create(parts[1], getColor(Integer.parseInt(parts[1]), (int) pl.getMoney()))));
            } else if (parts[0].equalsIgnoreCase("zombie")) {
                components.add(DComponent.create("- Зомби: ", TextColor.color(255, 172, 0)).append(
                                DComponent.create(parts[1], getColor(Integer.parseInt(parts[1]), pl.getZombies()))));
            } else if (parts[0].equalsIgnoreCase("bat")) {
                components.add(DComponent.create("- Летучие мыши: ", TextColor.color(255, 172, 0)).append(
                                DComponent.create(parts[1], getColor(Integer.parseInt(parts[1]), pl.getZombies()))));
            }
            nbts.put(parts[0].toLowerCase(), parts[1]);
        }
        nbts.put("trainer", type);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.lore(components);
        stack.setItemMeta(meta);
        for (Entry<String, String> entry : nbts.entrySet()) {
            stack = NBT.addNBT(stack, entry.getKey(), entry.getValue());
        }
        return stack;
    }

    private List<Component> getLore(String type) {
        switch (type) {
            case "strength" -> {
                return List.of(DComponent.create("Силы больше", TextColor.color(151, 151, 151))); }
            case "agility" -> {
                return List.of(DComponent.create("Уклонений больше", TextColor.color(151, 151, 151))); }
            case "needs" -> {
                return List.of(
                        DComponent.create("Потребностей меньше", TextColor.color(151, 151, 151))); }
            default -> { return List.of(); }
        }
    }

    private int getLevel(LittlePlayer pl, String type) {
        switch (type) {
            case "strength" -> { return  pl.getStrengthLvl(); }
            case "agility" -> { return pl.getAgilityLvl(); }
            case "needs" -> { return pl.getNeedsLvl(); }
            default -> { return 0; }
        }
    }

    private void addLevel(LittlePlayer pl, String type) {
        switch (type) {
            case "strength" -> pl.setStrengthLvl(pl.getStrengthLvl() + 1);
            case "agility" -> pl.setAgilityLvl(pl.getAgilityLvl() + 1);
            case "needs" -> pl.setNeedsLvl(pl.getNeedsLvl() + 1);
        }
        pl.updateTrainerSkills();
    }

    private TextColor getColor(int cost, int value) {
        if (cost >= value) {
            return TextColor.color(163, 0, 13);
        } else {
            return TextColor.color(0, 165, 0);
        }
    }

    private Component getName(String type, int lvl) {
        switch (type) {
            case "strength" -> { return DComponent.create("Сила", TextColor.color(198, 118, 0)).append(
                    DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            case "agility" -> { return DComponent.create("Ловкость", TextColor.color(77, 154, 0)).append(
                    DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            case "needs" -> { return DComponent.create("Потребности", TextColor.color(0, 143, 145)).append(
                    DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            default -> { return DComponent.create(""); }
        }
    }

    private Material getMat(String type) {
        switch (type) {
            case "strength" -> { return Material.DIAMOND_SWORD; }
            case "agility" -> { return Material.DIAMOND_BOOTS; }
            case "needs" -> { return Material.PAPER; }
            default -> { return Material.STONE; }
        }
    }

    @EventHandler
    public void onVillagerHurt(EntityDamageEvent e) {
        if (e.getEntity().name().equals(traderName)) {
            e.setCancelled(true);
        }
    }

}

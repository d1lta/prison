package me.d1lta.prison.entities.traders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.d1lta.prison.Main;
import me.d1lta.prison.entities.PrisonTrader;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrainerVillager extends PrisonTrader {

    public static Component name = DComponent.create("Тренер", TextColor.color(214, 144, 0));
    public static Component title = DComponent.create("Тренер", TextColor.color(46, 46, 46));


    public TrainerVillager(Location location) {
        super(location, name, 999, false);
    }

    public static void openInv(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, title);
        inv.setItem(1, getItem("strength", pl));
        inv.setItem(2, getItem("agility", pl));
        inv.setItem(3, getItem("needs", pl));
        pl.openInventory(inv);
    }

    private static ItemStack getItem(String type, LittlePlayer pl) {
        ItemStack stack = new ItemStack(getMat(type));
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(getName(type, getLevel(pl, type) + 1));
        ArrayList<Component> components = new ArrayList<>();
        Map<String, String> nbts = new HashMap<>();
        components.add(DComponent.create(""));
        components.addAll(getLore(type));
        components.add(DComponent.create(""));
        String[] parts;
        if (getLevel(pl, type) != 10) {
            components.add(DComponent.create("Требования: ", TextColor.color(255, 255, 0)));
            for (String it : Main.config.getConfig().getStringList("trainer." + type + ".level_" + (getLevel(pl, type) + 1))) {
                parts = it.split(":");
                if (parts[0].equalsIgnoreCase("money")) {
                    components.add(DComponent.create("- Деньги: ", TextColor.color(255, 172, 0)).append(DComponent.create(parts[1], getColor(Integer.parseInt(parts[1]), (int) pl.getMoney()))));
                } else if (parts[0].equalsIgnoreCase("zombie")) {
                    components.add(
                            DComponent.create("- Зомби: ", TextColor.color(255, 172, 0)).append(
                            DComponent.create(String.valueOf(pl.getZombies()), getColor(Integer.parseInt(parts[1]), pl.getZombies())).append(
                            DComponent.create("/", TextColor.color(156, 156, 156))).append(
                            DComponent.create(parts[1], TextColor.color(255, 172, 0)))));
                } else if (parts[0].equalsIgnoreCase("bat")) {
                    components.add(
                            DComponent.create("- Летучие мыши: ", TextColor.color(255, 172, 0)).append(
                            DComponent.create(String.valueOf(pl.getBats()), getColor(Integer.parseInt(parts[1]), pl.getBats()))).append(
                            DComponent.create("/", TextColor.color(156, 156, 156))).append(
                            DComponent.create(parts[1], TextColor.color(255, 172, 0))));
                }
                nbts.put(parts[0].toLowerCase(), parts[1]);
            }
        } else {
            components.add(DComponent.create("Максимальный уровень!",TextColor.color(255, 255, 0)));
            nbts.put("maxlvl", "0");
        }
        nbts.put("trainer", type);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.lore(components);
        stack.setItemMeta(meta);
        for (Entry<String, String> entry : nbts.entrySet()) { stack = NBT.addNBT(stack, entry.getKey(), entry.getValue()); }
        return stack;
    }

    private static List<Component> getLore(String type) {
        switch (type) {
            case "strength" -> { return List.of(DComponent.create("Силы больше", TextColor.color(151, 151, 151))); }
            case "agility" -> { return List.of(DComponent.create("Уклонений больше", TextColor.color(151, 151, 151))); }
            case "needs" -> { return List.of(DComponent.create("Потребностей меньше", TextColor.color(151, 151, 151))); }
            default -> { return List.of(); }
        }
    }

    private static int getLevel(LittlePlayer pl, String type) {
        switch (type) {
            case "strength" -> { return  pl.getStrengthLvl(); }
            case "agility" -> { return pl.getAgilityLvl(); }
            case "needs" -> { return pl.getNeedsLvl(); }
            default -> { return 0; }
        }
    }

    public static void addLevel(LittlePlayer pl, String type) {
        int lvl = getLevel(pl, type);
        if (lvl >= 10) { return; }
        switch (type) {
            case "strength" -> pl.setStrengthLvl(lvl + 1);
            case "agility" -> pl.setAgilityLvl(lvl + 1);
            case "needs" -> pl.setNeedsLvl(lvl + 1);
        }
        pl.updateTrainerSkills();
    }

    private static TextColor getColor(int cost, int value) {
        if (cost > value) {
            return TextColor.color(163, 0, 13);
        } else {
            return TextColor.color(0, 165, 0);
        }
    }

    private static Component getName(String type, int lvl) {
        if (lvl == 11) { lvl = 10; }
        switch (type) {
            case "strength" -> { return DComponent.create("Сила", TextColor.color(198, 118, 0)).append(DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            case "agility" -> { return DComponent.create("Ловкость", TextColor.color(77, 154, 0)).append(DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            case "needs" -> { return DComponent.create("Потребности", TextColor.color(0, 143, 145)).append(DComponent.create(String.format(" (%d-й уровень)", lvl))); }
            default -> { return DComponent.create(""); }
        }
    }

    private static Material getMat(String type) {
        switch (type) {
            case "strength" -> { return Material.DIAMOND_SWORD; }
            case "agility" -> { return Material.DIAMOND_BOOTS; }
            case "needs" -> { return Material.PAPER; }
            default -> { return Material.STONE; }
        }
    }
}

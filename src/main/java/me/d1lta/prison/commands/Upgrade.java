package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.ConfigUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import me.d1lta.prison.utils.NumberUtils;
import me.d1lta.prison.utils.Translate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Upgrade implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (!CheckUtils.checkForNull(pl.getItemInMainHand())) { return true; }
            ItemStack stack = pl.getItemInMainHand();
            String type = NBT.getStringNBT(stack, "type");
            if (type == null || type.isEmpty()) { return true; }
            int maxlvl = Main.config.getConfig().getConfigurationSection("upgrades." + type).getKeys(false).size();
            int currentlvl = NBT.getIntNBT(stack, "level");
            if (args.length == 1 && args[0].equals("max") && pl.isOp()) {
                ItemStack clone = pl.getItemInMainHand().clone();
                pl.getItemInMainHand().setAmount(0);
                pl.getInventory().setItem(pl.getHeldItemSlot(), getPrisonItem(pl, type, maxlvl, false, getEnchants(clone)));
                pl.closeInventory();
                return true;
            }
            if (currentlvl < maxlvl) {
                openUI(pl, type, currentlvl + 1, getEnchants(pl.getItemInMainHand()));
            } else {
                pl.sendMessage("Предмет уже максимального уровня!");
            }
            return true;
        }
        return false;
    }

    public static boolean checkCondition(LittlePlayer pl, String type, int lvl) {
        for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".requirements")) {
            String[] parts = it.split(":");
            Object query = switch (parts[0].toLowerCase()) {
                case "money" -> pl.getIntMoney();
                case "rats" -> pl.getRats();
                default -> pl.getBlocks(parts[0]);
            };
            if (Double.parseDouble(String.valueOf(query)) < Double.parseDouble(parts[1])) {
                return false;
            }
        }
        return true;
    }

    private void openUI(LittlePlayer pl, String type, int lvl, Map<String, Integer> enchants) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, CValues.get("Улучшение предмета", 255, 255, 0).create());
        inventory.setItem(1, getPrisonItem(pl, type, lvl, true, enchants));
        inventory.setItem(3, getUpgradeButton());
        for (int i = 0; i <= 4; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, getFiller());
            }
        }
        pl.openInventory(inventory);
    }

    private static ItemStack getFiller() {
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(DComponent.create(""));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getUpgradeButton() {
        ItemStack stack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(DComponent.create("Улучшить предмет", TextColor.color(100, 222, 117)));
        meta.lore(List.of(DComponent.create(""), CValues.get("Улучшить предмет до").create(), CValues.get("следующего уровня", 243, 182, 56).create()));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getPrisonItem(LittlePlayer pl, String type, int lvl, boolean upgrade, Map<String, Integer> enchants) {
        ItemStack tool = new ItemStack(Objects.requireNonNull(Material.getMaterial(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".type"))));
        tool = NBT.addNBT(tool, "level", Integer.valueOf(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".level"))));
        tool = NBT.addNBT(tool, "type", type);
        tool = NBT.addNBT(tool, "safe", "true");
        ItemMeta meta = tool.getItemMeta();
        meta.displayName(
                DComponent.create(ConfigUtils.getString("upgrades." + type + ".level_" + lvl + ".displayName"), TextColor.color(214, 144, 0)));
        if (upgrade) {
            meta.lore(itemUpgradeLore(pl, type, lvl, enchants));
        } else {
            meta.lore(itemLore(lvl, enchants));
        }
        Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".enchants").forEach(it -> {
            String[] parts = it.split(":");
            meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(parts[0]))), Integer.parseInt(parts[1]), true);
        });
        meta.setUnbreakable(true);
        tool.setItemMeta(meta);
        tool.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        if (enchants != null) {
            for (Entry<String, Integer> entry : enchants.entrySet()) {
                String k = entry.getKey();
                Integer v = entry.getValue();
                tool = NBT.addNBT(tool, k, v);
            }
        }
        return tool;
    }

    public static List<Component> itemLore(int lvl, Map<String, Integer> enchants) {
        List<Component> components = new ArrayList<>();
        if (enchants != null && !enchants.isEmpty()) {
            components.add(DComponent.create(""));
            components.add(CValues.get("Древние зачарования:", 180, 0, 190).create());
            enchants.forEach((k,v) -> components.add(Enchantments.getEnchantment(k).getColoredNameWithLevel(v)));
        }
        components.add(DComponent.create(""));
        components.add(DComponent.create(CValues.get("Уровень предмета >> "), CValues.get(String.valueOf(lvl), 250, 249, 86)));
        return components;
    }

    public static List<Component> itemUpgradeLore(LittlePlayer pl, String type, int lvl, Map<String, Integer> enchants) {
        List<Component> lore = new ArrayList<>();
        lore.add(DComponent.create(""));
        if (!enchants.isEmpty()) {
            lore.add(CValues.get("Древние зачарования:", 176, 0, 190).create());
            enchants.forEach((k,v) -> lore.add(Enchantments.getEnchantment(k).getColoredNameWithLevel(v)));
            lore.add(CValues.get("").create());
        }
        lore.add(CValues.get("Требования: ", 250, 250, 90).create());
        for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".requirements")) {
            String[] parts = it.split(":");
            Object query = switch (parts[0].toLowerCase()) {
                case "money" -> pl.getIntMoney();
                case "rats" -> pl.getRats();
                default -> pl.getBlocks(parts[0]);
            };
            CValues checker;
            if (Double.parseDouble(String.valueOf(query)) < Double.parseDouble(parts[1])) {
                checker = CValues.get(" ×", 255, 82, 68);
            } else {
                checker = CValues.get(" ✓", 85, 255, 31);
            }
            lore.add(DComponent.create(
                    CValues.get(Translate.upgrade(parts[0]) + " >> ", 255,255,255),
                    CValues.get(NumberUtils.withK((int) Double.parseDouble(query.toString())), 250, 249, 86),
                    CValues.get("/", 74, 74, 74), CValues.get(parts[1], 0, 255, 80), checker
            ));
        }
        lore.add(CValues.get("").create());
        lore.add(DComponent.create(CValues.get("Уровень предмета >> "), CValues.get(String.valueOf(lvl), 250, 250, 90)));
        lore.add(CValues.get("После улучшения предмета", 60, 60, 60).create());
        lore.add(CValues.get("древние зачарования сохраняются", 60, 60, 60).create());
        return lore;
    }

    public static Map<String, Integer> getEnchants(ItemStack item) {
        Map<String, Integer> map = new HashMap<>();
        NBT.getKeys(item).forEach(it -> {
            for (Enchantments e: Enchantments.values()) {
                if (it.equals(e.getName())) {
                    map.put(it, NBT.getIntNBT(item, it));
                }
            }
        });
        return map;
    }
}

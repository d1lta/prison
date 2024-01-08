package me.d1lta.prison.archive;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Upgrade implements CommandExecutor {

    Map<String, String> tools = Map.of(
            "shovel","лопата",
            "pickaxe", "кирка",
            "axe","топор",
            "shears", "ножницы"
    );

    Map<String, String> armor = Map.of(
            "helmet", "шлем",
            "chestplate", "нагрудник",
            "leggings","поножи",
            "boots","ботинки"
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (pl.getItemInMainHand().getType() == Material.AIR) {
                return false;
            }
            tools.forEach((k,v) -> {
                if (!NBT.getStringNBT(pl.getItemInMainHand(), "type").equals(k)) {
                    Bukkit.broadcastMessage("true");
                    return;
                }
                int clvl = NBT.getIntNBT(pl.getItemInMainHand(), "level");
                if (v.equals(tools.get("shears"))) {
                    if (clvl == 6) {
                        pl.sendMessage("Ваша " + v + " максимального уровня.");
                        return;
                    }
                } else {
                    if (clvl == 17) {
                        pl.sendMessage("Ваша " + v + " максимального уровня.");
                        return;
                    }
                }
                openUpgradeUI(pl, getUpgradedTool(clvl + 1, k), k, clvl + 1);
            });
            if (armor.containsKey(NBT.getStringNBT(pl.getItemInMainHand(), "type"))) {
                int clvl = NBT.getIntNBT(pl.getItemInMainHand(), "level");
                String type = NBT.getStringNBT(pl.getItemInMainHand(), "type");
                if (clvl == 15) {
                    pl.sendMessage(armor.get(type) + " максимального уровня.");
                    return true;
                }
                openUpgradeUI(pl, getUpgradedTool(clvl + 1, type), type, clvl + 1);
            }
        }
        return false;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getInventory().getType().equals(InventoryType.HOPPER)) {
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }
            AtomicBoolean upgrade = new AtomicBoolean(true);
            e.setCancelled(true);
            LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
            ItemStack stack = e.getCurrentItem();
            tools.keySet().forEach(k -> {
                if (NBT.getStringNBT(stack, "type").equals(k)) {
                    List<String> toCompare = Arrays.asList("sand", "gravel", "dirt", "stone", "coal_ore", "iron_ore", "gold_ore", "white_wool", "cobweb");
                    for (String s: toCompare) {
                        Main.config.getConfig().getStringList("upgrades." + NBT.getStringNBT(stack, "type") + ".level_" + NBT.getIntNBT(stack, "level") + ".requirements")
                                .forEach(it -> {
                            String[] parts = it.split(":");
                            if (s.equals(parts[0].toLowerCase())) {
                                if (Integer.parseInt(Jedis.get(pl.uuid + ".blocks." + s)) < NBT.getIntNBT(stack,  s)) {
                                    pl.sendMessage("Недостаточно блоков.");
                                    upgrade.set(false);
                                    pl.closeInventory();
                                }
                            }
                        });
                    }
                    if (Double.parseDouble(Jedis.get(pl.uuid + ".money")) >= NBT.getIntNBT(stack, "money")) {
                        if (upgrade.get()) {
                            Jedis.set(pl.uuid + ".money", String.valueOf(Double.parseDouble(Jedis.get(pl.uuid + ".money")) - NBT.getIntNBT(stack, "money")));
                            pl.getInventory().setItem(pl.getHeldItemSlot(), getUpgradedTool(NBT.getIntNBT(pl.getItemInMainHand(), "level") + 1, k));
                            pl.closeInventory();
                        }
                    } else {
                        upgrade.set(false);
                        pl.sendMessage("Недостаточно денег.");
                        pl.closeInventory();
                    }
                }
            });

        }
    }

    private void openUpgradeUI(LittlePlayer pl, ItemStack item, String type, int lvl) {
        Inventory UI = Bukkit.createInventory(null, InventoryType.HOPPER, "Улучшение");
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        List<String> list = Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".requirements");
        Map<String, Integer> toNBT = new HashMap<>();
        ArrayList<String> parts;
        for (String it: list) {
            parts = translate(it.split(":"));
            String[] nbtParts = it.split(":");
            lore.add(Component.text(makeColor(pl, parts.get(0), Integer.parseInt(parts.get(1))) + ": " + parts.get(1)));
            toNBT.put(nbtParts[0].toLowerCase(), Integer.valueOf(nbtParts[1].toLowerCase()));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
        item = NBT.applyNBTs(toNBT, item);
        UI.setItem(2, item);
        pl.openInventory(UI);
    }

    private String makeColor(LittlePlayer pl, String s, int v) {
        switch (s.toLowerCase()) {
            case "денег": {
                if (Double.parseDouble(Jedis.get(pl.uuid + ".money")) < v) {
                    return ChatColor.RED + s;
                } else {
                    return ChatColor.GREEN + s;
                }
            }
            case "крысы": {
                if (Double.parseDouble(Jedis.get(pl.uuid + ".rats")) < v) {
                    return ChatColor.RED + s;
                } else {
                    return ChatColor.GREEN + s;
                }
            }
            default:
                if (Integer.parseInt(Jedis.get(pl.uuid + ".blocks." + retranslate(s)))
                        < v) {
                    return ChatColor.RED + s;
                } else {
                    return ChatColor.GREEN + s;
                }
        }
    }

    private ArrayList<String> translate(String[] arr) {
        ArrayList<String> list = new ArrayList<>();
        for (String i: arr) {
            switch (i.toLowerCase()) {
                case "money" -> list.add("Денег");
                case "sand" -> list.add("Песок");
                case "dirt" -> list.add("Земля");
                case "gravel" -> list.add("Гравий");
                case "stone" -> list.add("Камень");
                case "coal_ore" -> list.add("Уголь");
                case "iron_ore" -> list.add("Железо");
                case "gold_ore" -> list.add("Золото");
                case "white_wool" -> list.add("Шерсть");
                case "cobweb" -> list.add("Паутина");
                case "rats" -> list.add("Крысы");
                default -> list.add(i);
            }
        }
        return list;
    }

    private String retranslate(String s) {
        return switch (s.toLowerCase()) {
            case "денег" -> "money";
            case "песок" -> "sand";
            case "земля" -> "dirt";
            case "гравий" -> "gravel";
            case "камень" -> "stone";
            case "уголь" -> "coal_ore";
            case "железо" -> "iron_ore";
            case "золото" -> "gold_ore";
            case "шерсть" -> "white_wool";
            case "паутина" -> "cobweb";
            case "крысы" -> "rats";
            default -> "err";
        };
    }

    public static ItemStack getUpgradedTool(int lvl, String type) {
        ItemStack tool = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".type")))));
        tool = NBT.addNBT(tool, "level", Integer.valueOf(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".level"))));
        tool = NBT.addNBT(tool, "type", type);
        tool = NBT.addNBT(tool, "safe", "true");
        ItemMeta meta = tool.getItemMeta();
        meta.displayName(Component.text(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".displayName"))));
        meta.lore(List.of(Component.text(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".displayLevel")))));
        Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".enchants").forEach(it -> {
            String[] parts = it.split(":");
            meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(parts[0]))), Integer.parseInt(parts[1]), true);
        });
        meta.setUnbreakable(true);
        tool.setItemMeta(meta);
        tool.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        return tool;
    }

}

package me.d1lta.prison.commands;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
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

public class Upgrade implements CommandExecutor, Listener {

    Map<String, String> tools = Map.of(
            "shovel","лопата",
            "pickaxe", "кирка",
            "axe","топор",
            "shears", "ножницы"
    );

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player pl) {
            if (pl.getInventory().getItemInMainHand().getType() == Material.AIR) {
                return false;
            }
            tools.forEach((k,v) -> {
                if (!NBT.getStringNBT(pl.getInventory().getItemInMainHand(), "type").equals(k)) {
                    return;
                }
                int clvl = NBT.getIntNBT(pl.getInventory().getItemInMainHand(), "level");
                if (clvl == 17) {
                    pl.sendMessage("Ваша " + v + " максимального уровня.");
                    return;
                }
                openUpgradeUI(pl, getUpgradedTool(clvl + 1, k), k, clvl + 1);
            });
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
            Player pl = (Player) e.getWhoClicked();
            ItemStack stack = e.getCurrentItem();
            tools.keySet().forEach(k -> {
                if (NBT.getStringNBT(stack, "type").equals(k)) {
                    List<String> toCompare = Arrays.asList("sand", "gravel", "dirt", "stone", "coal_ore", "iron_ore", "gold_ore", "white_wool", "cobweb");
                    for (String s: toCompare) {
                        Main.config.getConfig().getStringList(
                                "upgrades." + NBT.getStringNBT(stack, "type") + ".level_" + NBT.getIntNBT(stack, "level") + ".requirements"
                        ).forEach(it -> {
                            String[] parts = it.split(":");
                            if (s.equals(parts[0].toLowerCase())) {
                                if (Integer.parseInt(Jedis.get(pl.getUniqueId() + ".blocks." + s)) < NBT.getIntNBT(stack,  s)) {
                                    pl.sendMessage("Недостаточно блоков.");
                                    upgrade.set(false);
                                    pl.closeInventory();
                                }
                            }
                        });

                    }
                    if (Double.parseDouble(Jedis.get(pl.getUniqueId() + ".money")) >= NBT.getIntNBT(stack, "money")) {
                        if (upgrade.get()) {
                            Jedis.set(pl.getUniqueId() + ".money", String.valueOf(Double.parseDouble(Jedis.get(pl.getUniqueId() + ".money")) - NBT.getIntNBT(stack, "money")));
                            pl.getInventory().setItem(pl.getInventory().getHeldItemSlot(), getUpgradedTool(NBT.getIntNBT(pl.getInventory().getItemInMainHand(), "level") + 1, k));
                            pl.closeInventory();
                        }
                    } else {
                        upgrade.set(false);
                        pl.sendMessage("Недостаточно блоков.");
                        pl.closeInventory();
                    }
                }
            });
        }
    }

    private void openUpgradeUI(Player pl, ItemStack item, String type, int lvl) {
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

    private String makeColor(Player pl, String s, int v) {
        switch (s.toLowerCase()) {
            case "денег": {
                if (Double.parseDouble(Jedis.get(pl.getUniqueId() + ".money")) < v) {
                    return ChatColor.RED + s;
                } else {
                    return ChatColor.GREEN + s;
                }
            }
            default:
                if (Integer.parseInt(Jedis.get(pl.getUniqueId() + ".blocks." + retranslate(s)))
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
            default -> "err";
        };
    }

    public static ItemStack getUpgradedTool(int lvl, String type) {
        ItemStack tool = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".type")))));
        tool = NBT.addNBT(tool, "level", Integer.valueOf(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".level"))));
        tool = NBT.addNBT(tool, "type", type);
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

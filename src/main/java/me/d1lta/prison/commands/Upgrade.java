package me.d1lta.prison.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.ConfigUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import me.d1lta.prison.utils.NumberUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (pl.getItemInMainHand() == null || pl.getItemInMainHand().getType().equals(Material.AIR)) {
                return true;
            }
            ItemStack stack = pl.getItemInMainHand();
            String type = NBT.getStringNBT(stack, "type");
            int maxlvl = Main.config.getConfig().getConfigurationSection("upgrades." + type).getKeys(false).size();
            int currentlvl = NBT.getIntNBT(stack, "level");
            if (args.length == 1 && args[0].equals("max") && pl.isOp()) {
                pl.getItemInMainHand().setAmount(0);
                pl.getInventory().setItem(pl.getHeldItemSlot(), getPrisonItem(pl, type, maxlvl, false));
                pl.closeInventory();
                return true;
            }
            if (currentlvl < maxlvl) {
                openUI(pl, type, currentlvl + 1, true);
                return true;
            } else {
                pl.sendMessage("Предмет уже максимального уровня!");
                return true;
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
        }
        if (e.getView().title().equals(ComponentUtils.component("Улучшение предмета", TextColor.color(255,255,0)))) {
            e.setCancelled(true);
            if (Objects.equals(e.getCurrentItem(), getUpgradeButton())) {
                LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
                String type = NBT.getStringNBT(pl.getItemInMainHand(), "type");
                int currentlvl = NBT.getIntNBT(pl.getItemInMainHand(), "level");
                if (checkCondition(pl, type, currentlvl + 1)) {
                    for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + currentlvl + ".requirements")) {
                        String[] parts = it.split(":");
                        if (parts[0].equalsIgnoreCase("money")) {
                            pl.removeMoney(Integer.parseInt(parts[1]));
                            break;
                        }
                    }
                    pl.getItemInMainHand().setAmount(0);
                    pl.getInventory().setItem(pl.getHeldItemSlot(), getPrisonItem(pl, type, currentlvl + 1, false));
                    pl.sendMessage("Предмет улучшен!");
                    pl.closeInventory();
                } else {
                    pl.sendMessage("Требования не выполнены!");
                    pl.closeInventory();
                }
            }
        }
    }

    private static boolean checkCondition(LittlePlayer pl, String type, int lvl) {
        for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".requirements")) {
            String[] parts = it.split(":");
            Object query = switch (parts[0].toLowerCase()) {
                case "money" -> (int) Double.parseDouble(Jedis.get(pl.uuid + ".money"));
                case "rats" -> Jedis.get(pl.uuid + ".rats");
                default -> Integer.parseInt(Jedis.get(pl.uuid + ".blocks." + parts[0].toLowerCase()));
            };
            if (Double.parseDouble(String.valueOf(query)) < Double.parseDouble(parts[1])) {
                return false;
            }
        }
        return true;
    }

    private void openUI(LittlePlayer pl, String type, int lvl, boolean upgrade) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, ComponentUtils.component("Улучшение предмета", TextColor.color(255,255,0)));
        inventory.setItem(1, getPrisonItem(pl, type, lvl, upgrade));
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
        meta.displayName(ComponentUtils.component(""));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack getUpgradeButton() {
        ItemStack stack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(ComponentUtils.component("Улучшить предмет", TextColor.color(100, 222, 117)));
        meta.lore(List.of(
                ComponentUtils.component(""),
                ComponentUtils.component("Улучшить предмет до"),
                ComponentUtils.component("следующего уровня", TextColor.color(243, 182, 56))
        ));
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack getPrisonItem(LittlePlayer pl, String type, int lvl, boolean upgrade) {
        ItemStack tool = new ItemStack(Objects.requireNonNull(Material.getMaterial(ConfigUtils.getString("upgrades." + type + ".level_" + lvl + ".type"))));
        tool = NBT.addNBT(tool, "level", Integer.valueOf(Objects.requireNonNull(Main.config.getConfig().getString("upgrades." + type + ".level_" + lvl + ".level"))));
        tool = NBT.addNBT(tool, "type", type);
        tool = NBT.addNBT(tool, "safe", "true");
        ItemMeta meta = tool.getItemMeta();
        meta.displayName(ComponentUtils.component(ConfigUtils.getString("upgrades." + type + ".level_" + lvl + ".displayName"), TextColor.color(214, 144, 0)));
        if (upgrade) {
            meta.lore(itemUpgradeLore(pl, type, lvl));
        } else {
            meta.lore(itemLore(lvl));
        }
        Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".enchants").forEach(it -> {
            String[] parts = it.split(":");
            meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(parts[0]))), Integer.parseInt(parts[1]), true);
        });
        meta.setUnbreakable(true);
        tool.setItemMeta(meta);
        tool.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ATTRIBUTES);
        return tool;
    }

    public static List<Component> itemLore(int lvl) {
        return List.of(
                ComponentUtils.component(""),
                ComponentUtils.component("Уровень предмета >> ").append(
                        ComponentUtils.component(String.valueOf(lvl), TextColor.color(250, 249, 86))
        ));
    }

    public static List<Component> itemUpgradeLore(LittlePlayer pl, String type, int lvl) {
        List<Component> lore = new ArrayList<>();
        lore.add(ComponentUtils.component(""));
        lore.add(ComponentUtils.component("Требования: ").color(TextColor.color(250, 249, 86)));
        for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + lvl + ".requirements")) {
            String[] parts = it.split(":");
            Object query = switch (parts[0].toLowerCase()) {
                case "money" -> (int) Double.parseDouble(Jedis.get(pl.uuid + ".money"));
                case "rats" -> Jedis.get(pl.uuid + ".rats");
                default -> Integer.parseInt(Jedis.get(pl.uuid + ".blocks." + parts[0].toLowerCase()));
            };
            Component can;
            if (Double.parseDouble(String.valueOf(query)) < Double.parseDouble(parts[1])) {
                can = ComponentUtils.component(" ×",TextColor.color(255, 82, 68));
            } else {
                can = ComponentUtils.component(" ✓",TextColor.color(85, 255, 31));
            }
            lore.add(ComponentUtils.component( translate(parts[0]) + " >> ", TextColor.color(255,255,255)).append(
                            ComponentUtils.component(NumberUtils.withK((int) Double.parseDouble(query.toString())), TextColor.color(250, 249, 86))).append(
                            ComponentUtils.component("/", TextColor.color(74,74,74))).append(
                            ComponentUtils.component(parts[1], TextColor.color(0,255,80))).append(can));
        }
        lore.add(ComponentUtils.component(""));
        lore.add(ComponentUtils.component("Уровень предмета >> ").append(
                ComponentUtils.component(String.valueOf(lvl), TextColor.color(250, 249, 86))
        ));
        lore.add(ComponentUtils.component("После улучшения предмета", TextColor.color(59, 59, 59)));
        lore.add(ComponentUtils.component("древние зачарования сохраняются", TextColor.color(59, 59, 59)));
        return lore;
    }

    private static String translate(String s) {
            return switch (s.toLowerCase()) {
                case "money" -> "Денег";
                case "sand" -> "Песок";
                case "dirt" -> "Земля";
                case "gravel" -> "Гравий";
                case "stone" -> "Камень";
                case "coal_ore" -> "Уголь";
                case "iron_ore" -> "Железо";
                case "gold_ore" -> "Золото";
                case "white_wool" -> "Шерсть";
                case "cobweb" -> "Паутина";
                case "rats" -> "Крыс";
                default -> s;
        };
    }
}

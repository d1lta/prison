package me.d1lta.prison.commands;

import static me.d1lta.prison.utils.LocationUtils.spawnPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.Teleport;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.LocationUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Mine implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            if (args.length == 0) {
                openUI(pl);
                return true;
            }
            if (args.length == 1 && args[0].matches("-?(0|[1-9]\\d*)")) {
                int lvl = Integer.parseInt(Jedis.get(pl.uuid + ".lvl"));
                Map<Integer, String> mines = Map.of(
                        3, "stonemine",
                        5, "concrete",
                        7, "hell",
                        9, "desert",
                        12, "quartzmine",
                        14, "end",
                        16, "spider",
                        19, "quarry",
                        21, "icehills",
                        23,"obsmine");
                if (!mines.containsKey(Integer.parseInt(args[0]))) {
                    pl.sendMessage("Такой шахты не знаю.");
                    return true;
                }
                if (lvl >= Integer.parseInt(args[0]) || pl.isOp()) {
                    Teleport.tp(pl, pl.getLocation(), spawnPoint(mines.get(Integer.parseInt(args[0]))));
                    return true;
                }
            } else {
                pl.sendMessage("?");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("Локации")) {
            if (e.getCurrentItem() != null) {
                Teleport.tp(new LittlePlayer(e.getWhoClicked().getUniqueId()), e.getWhoClicked().getLocation(), LocationUtils.spawnPoint(NBT.getStringNBT(e.getCurrentItem(), "world")));
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }
        }
    }

    private static void openUI(LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 36, "Локации");
        for (ItemStack stack: getmines()) {
            if (stack.getType().equals(Material.KNOWLEDGE_BOOK)) {
                inv.setItem(27, stack);
            } else {
                inv.addItem(stack);
            }
        }
        pl.openInventory(inv);
    }

    private static List<ItemStack> getmines() {
        return List.of(
                mine(getMat(3), Component.text("Шахта"), getLore(3), getWorld(3)),
                mine(getMat(5), Component.text("Шахта"), getLore(5), getWorld(5)),
                mine(getMat(7), Component.text("Шахта"), getLore(7), getWorld(7)),
                mine(getMat(9), Component.text("Шахта"), getLore(9), getWorld(9)),
                mine(getMat(12), Component.text("Шахта"), getLore(12), getWorld(12)),
                mine(getMat(14), Component.text("Шахта"), getLore(14), getWorld(14)),
                mine(getMat(16), Component.text("Шахта"), getLore(16), getWorld(16)),
                mine(getMat(19), Component.text("Шахта"), getLore(19), getWorld(19)),
                mine(getMat(21), Component.text("Шахта"), getLore(21), getWorld(21)),
                mine(getMat(23), Component.text("Шахта"), getLore(23), getWorld(23)),
                mine(getMat(0), Component.text("Шахта"), getLore(0), getWorld(0))
        );
    }

    private static Material getMat(int i) {
        return Material.getMaterial(Objects.requireNonNull(Main.config.getConfig().get("minelore." + i + ".M")).toString());
    }

    private static String getWorld(int i) {
        return Objects.requireNonNull(Main.config.getConfig().get("minelore." + i + ".nbt")).toString();
    }

    private static List<Component> getLore(int i) {
        List<Component> list = new ArrayList<>();
        Main.config.getConfig().getStringList("minelore." + i + ".lore").forEach(it -> list.add(Component.text(it)));
        return list;
    }

    private static ItemStack mine(Material material, Component name, List<Component> lore, String nbt) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(name);
        meta.lore(lore);
        stack.setItemMeta(meta);
        stack = NBT.addNBT(stack, "world", nbt);
        return stack;
    }

}

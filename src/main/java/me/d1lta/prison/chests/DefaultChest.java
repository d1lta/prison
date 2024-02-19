package me.d1lta.prison.chests;

import java.util.Objects;
import java.util.Random;
import me.d1lta.prison.Main;
import me.d1lta.prison.items.Apple;
import me.d1lta.prison.items.Armor;
import me.d1lta.prison.items.Arrow;
import me.d1lta.prison.items.Chicken;
import me.d1lta.prison.items.Key;
import me.d1lta.prison.items.ToiletPaper;
import me.d1lta.prison.items.Tool;
import me.d1lta.prison.items.VaultAccess;
import me.d1lta.prison.items.Weapon;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DefaultChest implements Listener {

    private static Location loc;
    private static final String path = "chests.case.";

    public static void initLoc() {
        loc = new Location(
                Bukkit.getWorld(Objects.requireNonNull(Main.config.getConfig().getString(path + "world"))),
                Main.config.getConfig().getDouble(path + "x"),
                Main.config.getConfig().getDouble(path + "y"),
                Main.config.getConfig().getDouble(path + "z"));
    }

    public static void spawnChest() {
        loc.getWorld().getBlockAt(loc).setType(Material.CHEST);
        BlockData blockData = loc.getBlock().getBlockData();
        ((Directional) blockData).setFacing(getFacing(Objects.requireNonNull(Main.config.getConfig().getString(path + "facing"))));
        loc.getBlock().setBlockData(blockData);
    }

    private static BlockFace getFacing(String face) {
        return switch (face) {
            case "east" -> BlockFace.EAST;
            case "south" -> BlockFace.SOUTH;
            case "west" -> BlockFace.WEST;
            default -> BlockFace.NORTH;
        };
    }


    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getLocation().equals(loc) && e.getClickedBlock().getType().equals(Material.CHEST)) {
            e.setCancelled(true);
            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(Key.getKey())) {
                LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
                pl.getItemInMainHand().setAmount(pl.getItemInMainHand().getAmount() - 1);
                openUI(pl);
            }
        }
    }

    private void openUI(LittlePlayer pl) {
        Inventory inventory = Bukkit.createInventory(null, 27, DComponent.create("Кейс", TextColor.color(130, 133, 134)));
        for (String it : Main.config.getConfig().getConfigurationSection("items").getKeys(false)) {
            int i = new Random().nextInt(1, 101);
            if (i <= Main.config.getConfig().getInt("items." + it + ".chance")) {
                inventory.addItem(getItem(it, new Random().nextInt(1, Main.config.getConfig().getInt("items." + it + ".max_count") + 1)));
            }
        }
        pl.openInventory(refresh(inventory));
    }

    private ItemStack getItem(String type, int amount) {
        ItemStack stack = switch (type) {
            case "apple" -> Apple.getApple();
            case "chicken" -> Chicken.getChicken();
            case "paper" -> ToiletPaper.getPaper();
            case "arrow" -> Arrow.getArrow();
            case "vault" -> VaultAccess.getAccess();
            case "rare_pickaxe" -> Tool.rare_pickaxe();
            case "helmet" -> Armor.helmet();
            case "chestplate" -> Armor.chestplate();
            case "leggings" -> Armor.leggings();
            case "boots" -> Armor.boots();
            case "sword" -> Weapon.sword();
            case "bow" -> Weapon.bow();
            default -> Apple.getApple();
        };
        stack.setAmount(amount);
        return stack;
    }

    private static Inventory refresh(Inventory inv) {
        Inventory newinv = Bukkit.createInventory(null, 27, DComponent.create("Кейс", TextColor.color(130, 133, 134)));
        for (ItemStack stack : inv.getContents()) {
            if (stack == null) {
                continue;
            }
            int slot = new Random().nextInt(1, 27);
            if (newinv.getItem(slot) != null) {
                newinv.setItem(slot, stack);
            } else {
                if (newinv.getItem(slot) != null) {
                    slot = new Random().nextInt(1, 27);
                    newinv.setItem(slot, stack);
                } else {
                    slot = new Random().nextInt(1, 27);
                    newinv.setItem(slot, stack);
                }
            }
        }
        return newinv;
    }

}

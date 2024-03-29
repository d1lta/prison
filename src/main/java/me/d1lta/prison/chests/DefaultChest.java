package me.d1lta.prison.chests;

import static me.d1lta.prison.utils.LocationUtils.getFacing;

import java.util.Objects;
import java.util.Random;
import me.d1lta.prison.Main;
import me.d1lta.prison.items.Apple;
import me.d1lta.prison.items.Armor;
import me.d1lta.prison.items.Arrow;
import me.d1lta.prison.items.Chicken;
import me.d1lta.prison.items.ElderStar;
import me.d1lta.prison.items.OblivionDust;
import me.d1lta.prison.items.ToiletPaper;
import me.d1lta.prison.items.Tool;
import me.d1lta.prison.items.VaultAccess;
import me.d1lta.prison.items.Weapon;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DefaultChest {

    public static Location defaultChestLoc;
    private static final String path = "chests.case.";

    public static void initLoc() {
        defaultChestLoc = new Location(
                Bukkit.getWorld(Objects.requireNonNull(Main.config.getConfig().getString(path + "world"))),
                Main.config.getConfig().getDouble(path + "x"),
                Main.config.getConfig().getDouble(path + "y"),
                Main.config.getConfig().getDouble(path + "z"));
    }

    public static void spawnChest() {
        defaultChestLoc.getWorld().getBlockAt(defaultChestLoc).setType(Material.CHEST);
        BlockData blockData = defaultChestLoc.getBlock().getBlockData();
        ((Directional) blockData).setFacing(getFacing(Objects.requireNonNull(Main.config.getConfig().getString(path + "facing"))));
        defaultChestLoc.getBlock().setBlockData(blockData);
    }

    public static void openCaseUI(LittlePlayer pl) {
        Inventory inventory = Bukkit.createInventory(null, 27, CValues.get("Кейс", 130, 133, 134).create());
        for (String it : Main.config.getConfig().getConfigurationSection("items").getKeys(false)) {
            int i = new Random().nextInt(1, 101);
            if (i <= Main.config.getConfig().getInt("items." + it + ".chance")) {
                inventory.addItem(getItem(it, new Random().nextInt(1, Main.config.getConfig().getInt("items." + it + ".max_count") + 1)));
            }
        }
        pl.openInventory(refresh(inventory));
    }

    private static ItemStack getItem(String type, int amount) {
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
            case "star" -> ElderStar.getStar();
            case "oblivion_dust" -> OblivionDust.getOblivionDust();
            default -> Apple.getApple();
        };
        stack.setAmount(amount);
        return stack;
    }

    private static Inventory refresh(Inventory inv) {
        Inventory newInv = Bukkit.createInventory(null, 27, CValues.get("Кейс", 130, 133, 134).create());
        for (ItemStack stack : inv.getContents()) {
            if (stack == null) {
                continue;
            }
            int slot = new Random().nextInt(1, 27);
            if (newInv.getItem(slot) != null) {
                newInv.setItem(slot, stack);
            } else {
                if (newInv.getItem(slot) != null) {
                    slot = new Random().nextInt(1, 27);
                    newInv.setItem(slot, stack);
                } else {
                    slot = new Random().nextInt(1, 27);
                    newInv.setItem(slot, stack);
                }
            }
        }
        return newInv;
    }

}

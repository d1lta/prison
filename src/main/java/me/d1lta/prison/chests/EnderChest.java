package me.d1lta.prison.chests;

import static me.d1lta.prison.utils.LocationUtils.getFacing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

public class EnderChest {

    public static Location enderChestLoc;
    private static final String path = "chests.ender_chest.";
    public static final List<UUID> opening = new ArrayList<>();

    public static void initLoc() {
        enderChestLoc = new Location(Bukkit.getWorld(Objects.requireNonNull(Main.config.getConfig().getString(path + "world"))),
                Main.config.getConfig().getDouble(path + "x"),
                Main.config.getConfig().getDouble(path + "y"),
                Main.config.getConfig().getDouble(path + "z"));
    }

    public static void spawnChest() {
        enderChestLoc.getWorld().getBlockAt(enderChestLoc).setType(Material.ENDER_CHEST);
        BlockData blockData = enderChestLoc.getBlock().getBlockData();
        ((Directional) blockData).setFacing(getFacing(Objects.requireNonNull(Main.config.getConfig().getString(path + "facing"))));
        enderChestLoc.getBlock().setBlockData(blockData);
    }

    public static void openChest(LittlePlayer pl, Boolean advanced, ItemStack keyStack) {
        keyStack.setAmount(keyStack.getAmount() - 1);
        new ElderChest().elderCase(advanced, pl);
        opening.add(pl.uuid);
    }
}

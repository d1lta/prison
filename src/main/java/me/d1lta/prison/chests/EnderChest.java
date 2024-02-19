package me.d1lta.prison.chests;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnderChest implements Listener {

    private static Location loc;
    private static final String path = "chests.ender_chest.";

    public static void initLoc() {
        loc = new Location(
                Bukkit.getWorld(Objects.requireNonNull(Main.config.getConfig().getString(path + "world"))),
                Main.config.getConfig().getDouble(path + "x"),
                Main.config.getConfig().getDouble(path + "y"),
                Main.config.getConfig().getDouble(path + "z"));
    }

    public static void spawnChest() {
        loc.getWorld().getBlockAt(loc).setType(Material.ENDER_CHEST);
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

    public static List<UUID> opening = new ArrayList<>();

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (e.getClickedBlock().getLocation().equals(loc) && e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            if (pl.getItemInMainHand() == null || pl.getItemInMainHand().getType() == Material.AIR || pl.getItemInMainHand().getAmount() == 0) { return; }
            if (opening.contains(pl.uuid)) {
                pl.sendMessage(DComponent.create("Дождитесь открытие древнего кейса!", TextColor.color(255, 172, 0)));
                return;
            }
            ItemStack stack = pl.getItemInMainHand();
            if (NBT.getStringNBT(stack, "type").equals("elder_key")) {
                e.setCancelled(true);
                stack.setAmount(stack.getAmount() - 1);
                new ElderChest().elderCase(true, pl);
                opening.add(pl.uuid);
            } else if (NBT.getStringNBT(stack, "type").equals("broken_elder_key")) {
                e.setCancelled(true);
                stack.setAmount(stack.getAmount() - 1);
                new ElderChest().elderCase(false, pl);
                opening.add(pl.uuid);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().title().equals(DComponent.create("супер кейс", TextColor.color(130, 133, 134)))) {
            e.setCancelled(true);
        }
    }
}

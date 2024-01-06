package me.d1lta.prison.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import me.d1lta.prison.AllowedBlocks;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Sell;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.mines.Mine;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        AtomicReference<Boolean> status = new AtomicReference<>(false);
        Map<Location, Location> allowedLocations = new HashMap<>(Map.of(
                Mine.getLocation("dirtmine", 1), Mine.getLocation("dirtmine", 2),
                Mine.getLocation("stonemine", 1), Mine.getLocation("stonemine", 2),
                Mine.getLocation("concrete", 1), Mine.getLocation("concrete", 2),
                Mine.getLocation("hell", 1), Mine.getLocation("hell", 2),
                Mine.getLocation("desert", 1), Mine.getLocation("desert", 2),
                Mine.getLocation("quartzmine", 1), Mine.getLocation("quartzmine", 2),
                Mine.getLocation("end", 1), Mine.getLocation("end", 2),
                Mine.getLocation("spider", 1), Mine.getLocation("spider", 2),
                Mine.getLocation("quarry", 1), Mine.getLocation("quarry", 2),
                Mine.getLocation("icehills", 1), Mine.getLocation("icehills", 2)
        ));
        allowedLocations.put(
                Mine.getLocation("obsmine", 1), Mine.getLocation("obsmine", 2)
        );
        allowedLocations.forEach((loc1, loc2) -> {
            for (int x = (int) loc1.getX(); x <= loc2.getX(); x++) {
                for (int z = (int) loc1.getZ(); z <= loc2.getZ(); z++) {
                    for (int y = (int) loc1.getY(); y <= loc2.getY(); y++) {
                        if (Objects.equals(e.getBlock().getLocation(), new Location(loc1.getWorld(), x, y, z))) {
                            status.set(true);
                        }
                    }
                }
            }
        });
        if (status.get()) {
            ItemStack stack = new ItemStack(Material.AIR);
            switch (e.getBlock().getType()) {
                case DIRT -> stack = AllowedBlocks.dirt();
                case GRAVEL -> stack = AllowedBlocks.gravel();
                case SAND -> stack = AllowedBlocks.sand();
                case STONE -> stack = AllowedBlocks.stone();
                case COAL_ORE -> stack = AllowedBlocks.coal_ore();
                case IRON_ORE -> stack = AllowedBlocks.iron_ore();
                case GOLD_ORE -> stack = AllowedBlocks.gold_ore();
                case WHITE_CONCRETE_POWDER -> stack = AllowedBlocks.white_concrete_block();
                case LIGHT_GRAY_CONCRETE_POWDER -> stack = AllowedBlocks.light_gray_concrete_powder();
                case NETHERRACK -> stack = AllowedBlocks.netherrack();
                case SOUL_SAND -> stack = AllowedBlocks.soul_sand();
                case NETHER_QUARTZ_ORE -> stack = AllowedBlocks.nether_quartz_ore();
                case SANDSTONE -> stack = AllowedBlocks.sandstone();
                case QUARTZ_BLOCK -> stack = AllowedBlocks.quartz_block();
                case PRISMARINE_BRICKS -> stack = AllowedBlocks.prismarine_bricks();
                case PURPUR_BLOCK -> stack = AllowedBlocks.purpur_block();
                case END_STONE_BRICKS -> stack = AllowedBlocks.end_stone_bricks();
                case WHITE_WOOL -> stack = AllowedBlocks.white_wool();
                case COBWEB -> stack = AllowedBlocks.cobweb();
                case TERRACOTTA -> stack = AllowedBlocks.terracotta();
                case BROWN_GLAZED_TERRACOTTA -> stack = AllowedBlocks.brown_glazed_terracotta();
                case ICE -> stack = AllowedBlocks.ice();
                case PACKED_ICE -> stack = AllowedBlocks.packed_ice();
                case PURPLE_TERRACOTTA -> stack = AllowedBlocks.purple_terracotta();
                case OBSIDIAN -> stack = AllowedBlocks.obsidian();
            }
            e.getPlayer().getInventory().addItem(stack);
            if (AutoSell.players.contains(e.getPlayer())) {
                Sell.sell(e.getPlayer());
            }
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks." + e.getBlock().getType().name().toLowerCase(), String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getUniqueId() + ".blocks." + e.getBlock().getType().name().toLowerCase())) + 1));
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks", String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getUniqueId() + ".blocks")) + 1));
        } else {
            if (!(e.getPlayer().isOp() && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
                e.setCancelled(true);
            }
        }
        e.setDropItems(false);
        e.setExpToDrop(0);
    }
}

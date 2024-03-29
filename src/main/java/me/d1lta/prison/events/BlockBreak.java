package me.d1lta.prison.events;


import static me.d1lta.prison.Main.wood;

import java.util.Random;
import me.d1lta.prison.Main;
import me.d1lta.prison.Sell;
import me.d1lta.prison.boosters.BlockBoostHandler;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.items.Key;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (e.getBlock().getLocation().getWorld().getName().equals("hub")) {
            if (e.getBlock().getType().equals(Material.OAK_WOOD)) {
                Location loc = e.getBlock().getLocation();
                simulate(pl, loc);
                wood.add(loc);
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                    loc.getBlock().setType(Material.OAK_WOOD);
                    wood.remove(loc);
                }, 200L);
                e.getBlock().setType(Material.AIR);
            }
        }
        if (MineUtils.isAllowedToBreakBlock(e.getBlock().getLocation())) {
            simulate(pl, e.getBlock().getLocation());
            if (e.getBlock().getType().equals(Material.ICE)) {
                e.getBlock().setType(Material.AIR);
            }
        } else {
            if (!(e.getPlayer().isOp() && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
                e.setCancelled(true);
            }
        }
        e.setDropItems(false);
        e.setExpToDrop(0);
    }

    public static void simulate(LittlePlayer pl, Location loc) {
        Material mat = loc.getBlock().getType();
        if (AutoSell.uuids.contains(pl.uuid)) {
            Sell.sell(pl, Main.config.getConfig().getDouble("prices." + mat.name().toLowerCase()));
        } else {
            pl.getInventory().addItem(MineUtils.getPrisonBlock(mat));
        }
        pl.addBlock();
        pl.addBlock(mat);
        BlockBoostHandler.add(pl, 1);
        if (new Random().nextInt(1, 301) == 100) {
            pl.getInventory().addItem(Key.getKey());
            pl.sendMessage("Вы нашли ключ!");
        }
    }

}


package me.d1lta.prison.events;

import java.util.Random;
import me.d1lta.prison.Sell;
import me.d1lta.prison.boosters.BlockBoostHandler;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.items.Key;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (MineUtils.isAllowedToBreakBlock(e.getBlock().getLocation())) {
            simulate(new LittlePlayer(e.getPlayer().getUniqueId()), e.getBlock().getLocation());
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
        pl.getInventory().addItem(MineUtils.getPrisonBlock(mat));
        if (AutoSell.uuids.contains(pl.uuid)) {
            Sell.sell(pl);
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

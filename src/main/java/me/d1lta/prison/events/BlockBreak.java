package me.d1lta.prison.events;

import java.util.Random;
import java.util.UUID;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Sell;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.items.Key;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (MineUtils.isAllowedToBreakBlock(e.getBlock().getLocation())) {
            e.getPlayer().getInventory().addItem(MineUtils.getPrisonBlock(e.getBlock().getType()));
            if (AutoSell.uuids.contains(e.getPlayer().getUniqueId())) {
                Sell.sell(new LittlePlayer(e.getPlayer().getUniqueId()));
            }
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks." + e.getBlock().getType().name().toLowerCase(), String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getUniqueId() + ".blocks." + e.getBlock().getType().name().toLowerCase())) + 1));
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks", String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getUniqueId() + ".blocks")) + 1));
            if (new Random().nextInt(1,301) == 100) {
                e.getPlayer().getInventory().addItem(Key.getKey());
                e.getPlayer().sendMessage("Вы нашли ключ!");
            }
        } else {
            if (!(e.getPlayer().isOp() && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
                e.setCancelled(true);
            }
        }
        e.setDropItems(false);
        e.setExpToDrop(0);
    }

}

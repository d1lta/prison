package me.d1lta.prison.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!(e.getPlayer().isOp() && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
            e.setCancelled(true);
        }
    }

}

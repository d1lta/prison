package me.d1lta.prison.events;

import me.d1lta.prison.utils.NBT;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDrop implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (NBT.getStringNBT(e.getItemDrop().getItemStack(), "safe").equals("true")) {
            e.setCancelled(true);
        }
    }

}

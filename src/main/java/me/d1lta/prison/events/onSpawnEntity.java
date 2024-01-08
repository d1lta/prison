package me.d1lta.prison.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntitySpawnEvent;

public class onSpawnEntity implements Listener {

    @EventHandler
    public void disableSpawnEntity(EntitySpawnEvent e) {
        if (e.getEntity().getEntitySpawnReason().equals(SpawnReason.NATURAL)) {
            e.setCancelled(true);
        }
    }

}

package me.d1lta.prison.events;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPDisabler implements Listener {

    public static List<String> worlds = List.of("hub");

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && worlds.contains(e.getEntity().getWorld().getName())) {
            e.setCancelled(true);
        }
    }

}

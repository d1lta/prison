package me.d1lta.prison.events;

import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerFaction implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                if (new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

package me.d1lta.prison.events;

import me.d1lta.prison.Jedis;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerFaction implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                if (Jedis.get(((Player) e.getDamager()).getPlayer().getUniqueId() + ".faction").equals(Jedis.get(((Player) e.getEntity()).getPlayer().getUniqueId() + ".faction"))) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

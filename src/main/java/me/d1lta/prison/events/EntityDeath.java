package me.d1lta.prison.events;

import java.util.Objects;
import me.d1lta.prison.mobs.Rat;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onRatDeath(EntityDeathEvent e) {
        if (Objects.equals(e.getEntity().customName(), Rat.ratName) && e.getEntity().getType().equals(EntityType.SILVERFISH)) {
            if (e.getEntity().getKiller() != null && e.getEntity().getKiller() instanceof Player) {
                new LittlePlayer(e.getEntity().getKiller().getUniqueId()).addRat();
            }
        }
    }

}

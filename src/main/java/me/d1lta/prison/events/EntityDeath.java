package me.d1lta.prison.events;

import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.mobs.Rat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onRatDeath(EntityDeathEvent e) {
        if (Objects.equals(e.getEntity().customName(), Rat.ratName) && e.getEntity().getType().equals(EntityType.SILVERFISH)) {
            Jedis.set(
                    Objects.requireNonNull(e.getEntity().getKiller()).getUniqueId() + ".rats",
                    String.valueOf(Integer.parseInt(Jedis.get(e.getEntity().getKiller().getUniqueId() + ".rats")) + 1)
            );
        }
    }

}

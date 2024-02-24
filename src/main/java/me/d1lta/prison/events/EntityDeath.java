package me.d1lta.prison.events;

import me.d1lta.prison.entities.bosses.Vindicator;
import me.d1lta.prison.entities.mobs.PrisonBat;
import me.d1lta.prison.entities.mobs.PrisonRat;
import me.d1lta.prison.entities.mobs.PrisonZombie;
import me.d1lta.prison.utils.BossUtils;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            LittlePlayer pl = new LittlePlayer(e.getEntity().getKiller().getUniqueId());
            if (e.getEntity().name().equals(PrisonZombie.name)) { pl.addZombie(); } // zombie kill
            else if (e.getEntity().name().equals(PrisonBat.name)) { pl.addBat(); } // bat kill
            else if (e.getEntity().name().equals(PrisonRat.name)) { pl.addRat(); } // rat kill
            else if (e.getEntity().name().equals(Vindicator.name)) { // Vindicator kill
                BossUtils.giveMoney(Vindicator.name, Vindicator.multiplier, Vindicator.damageDealers);
                Vindicator.damageDealers.clear();
            }
        }
        e.getDrops().clear();
        e.setDroppedExp(0);
    }
}

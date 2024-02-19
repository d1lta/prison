package me.d1lta.prison.events;

import me.d1lta.prison.mobs.PBat;
import me.d1lta.prison.mobs.PZombie;
import me.d1lta.prison.mobs.Rat;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        e.setDroppedExp(0);
        Component name = e.getEntity().customName();
        EntityType type = e.getEntity().getType();
        if (name == null) { return; }
        if (name.equals(Rat.ratName) && type.equals(EntityType.SILVERFISH) && e.getEntity().getKiller() != null) {
            new LittlePlayer(e.getEntity().getKiller().getUniqueId()).addRat();
        } else if (name.equals(PZombie.zombieName) && type.equals(EntityType.ZOMBIE) && e.getEntity().getKiller() != null) {
            new LittlePlayer(e.getEntity().getKiller().getUniqueId()).addZombie();
        } else if (name.equals(PBat.batName) && type.equals(EntityType.BAT) && e.getEntity().getKiller() != null) {
            new LittlePlayer(e.getEntity().getKiller().getUniqueId()).addBat();
        }
    }

}

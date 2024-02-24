package me.d1lta.prison.events;

import me.d1lta.prison.entities.mobs.PrisonRat;
import me.d1lta.prison.entities.traders.ElderVillager;
import me.d1lta.prison.entities.traders.StartVillager;
import me.d1lta.prison.entities.traders.TrainerVillager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        Component name = e.getEntity().name();
        if (name.equals(PrisonRat.name)) { ((LivingEntity) e.getEntity()).setAI(true); } // Добавление AI к крысе
        else if (name.equals(ElderVillager.name)) { e.setCancelled(true); } // Урон по древнему жителю
        else if (name.equals(StartVillager.name)) { e.setCancelled(true); } // Урон по стартовому жителю
        else if (name.equals(TrainerVillager.name)) { e.setCancelled(true); } // Урон по тренеру
    }

}

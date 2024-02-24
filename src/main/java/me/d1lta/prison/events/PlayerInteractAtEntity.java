package me.d1lta.prison.events;

import me.d1lta.prison.entities.traders.ElderVillager;
import me.d1lta.prison.entities.traders.StartVillager;
import me.d1lta.prison.entities.traders.TrainerVillager;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntity implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());

        if (e.getRightClicked() instanceof Villager) { // Торговцы
            if (e.getRightClicked().name().equals(ElderVillager.name)) { // Древний житель
                ElderVillager.openInv(pl);
            } else if (e.getRightClicked().name().equals(StartVillager.name)) { // Стартовый житель
                StartVillager.openInv(pl);
            } else if (e.getRightClicked().name().equals(TrainerVillager.name)) { // Тренер
                TrainerVillager.openInv(pl);
            }
            e.setCancelled(true);
        }
    }

}

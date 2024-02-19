package me.d1lta.prison.events;

import java.util.List;
import java.util.Random;
import me.d1lta.prison.enums.TrainerSkills;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPEvents implements Listener {

    public static List<String> worlds = List.of("hub");

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && worlds.contains(e.getEntity().getWorld().getName())) {
            e.setCancelled(true);
        } else {
            LittlePlayer pl = new LittlePlayer(e.getDamager().getUniqueId());
            if (TrainerSkills.getSkills(pl.getAgilityLvl()).getAgility_boost() > new Random().nextInt(1, 101)) {
                pl.sendMessage(DComponent.create("Вы уклонились!"));
                e.setCancelled(true);
            }
            e.setDamage(e.getFinalDamage() + TrainerSkills.getSkills(pl.getStrengthLvl()).getStrength_boost());
        } // TODO: Оптимизировать чтобы бд при каждом ударе не охуевала
    }

}

package me.d1lta.prison.enchants.enchantments.armor;

import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jetbrains.annotations.Nullable;

public class CatGrace implements Enchantment, Listener {

    static Enchantments ench = Enchantments.CATGRACE;

    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) { return; }
        if (!e.getCause().equals(DamageCause.FALL)) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getEntity().getUniqueId()).getBoots(), 1, 1, 1)) {
            switch (getLVL(new LittlePlayer(e.getEntity().getUniqueId()).getBoots(), ench)) {
                case 1 -> e.setDamage(e.getFinalDamage() / 1.3); // 40 / 2.0 = 20
                case 2 -> e.setDamage(e.getFinalDamage() / 1.6); // 40 / 1.6 = 25
                case 3 -> e.setDamage(e.getFinalDamage() / 2.0); // 40 / 1.3 = 30
            }
        }
    }

    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl, double damage) { }
}

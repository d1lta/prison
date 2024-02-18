package me.d1lta.prison.enchants.enchantments.armor;

import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Nullable;

public class Fiery implements Enchantment, Listener {

    static Enchantments ench = Enchantments.FIERY;

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getEntity().getUniqueId()).getLeggings(), 30, 23, 15)) {
            action(null, null, new LittlePlayer(e.getDamager().getUniqueId()), 0, 0);
        }
    }

    @Override
    public void action(@Nullable Location location, @Nullable LittlePlayer summoner, LittlePlayer victim, int lvl, double damage) {
        victim.burn(40);
    }
}

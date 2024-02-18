package me.d1lta.prison.enchants.enchantments.sword;

import me.d1lta.prison.Main;
import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class Boxer implements Enchantment, Listener {

    static Enchantments ench = Enchantments.BOXER;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), 140, 90, 70)) {
            action(null, null, new LittlePlayer(e.getEntity().getUniqueId()), getLVL(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), ench), 0);
        }
    }

    @Override
    public void action(@Nullable Location location, @Nullable LittlePlayer summoner, LittlePlayer victim, int lvl, double damage) {
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            switch (lvl) {
                case 1 -> victim.addVelocity(new Vector(0, 0.5, 0));
                case 2 -> victim.addVelocity(new Vector(0, 1, 0));
                case 3 -> victim.addVelocity(new Vector(0, 1.5, 0));
            }
        }, 1L);
    }
}

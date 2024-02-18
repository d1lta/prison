package me.d1lta.prison.enchants.enchantments.sword;

import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

public class Vortex implements Enchantment, Listener {

    static Enchantments ench = Enchantments.VORTEX;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), 90, 70, 50)) {
            action(null, new LittlePlayer(e.getDamager().getUniqueId()), new LittlePlayer(e.getEntity().getUniqueId()),
                    getLVL(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), ench), e.getFinalDamage());
        }
    }

    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, LittlePlayer victim, int lvl, double damage) {
        AtomicInteger countOfDamage = new AtomicInteger(0);
        switch (lvl) {
            case 1 -> countOfDamage.set(1);
            case 2 -> countOfDamage.set(2);
            case 3 -> countOfDamage.set(3);
        }
        AtomicInteger i = new AtomicInteger(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                switch (i.get()) {
                    case 0 -> victim.damage(damage, summoner.castToEntity());
                    case 1 -> victim.damage(damage / 1.5, summoner.castToEntity());
                    case 2 -> victim.damage(damage / 2, summoner.castToEntity());
                }
                i.set(i.get() + 1);
                if (i.get() == countOfDamage.get()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 15L, 15L);
    }
}

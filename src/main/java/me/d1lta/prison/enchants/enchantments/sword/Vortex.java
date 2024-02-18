package me.d1lta.prison.enchants.enchantments.sword;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enchants.book.VortexBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Vortex implements Listener {

    static Enchantments ench = Enchantments.VORTEX;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer damager = new LittlePlayer(e.getDamager().getUniqueId());
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            if (VortexBook.applicableTo.contains(damager.getItemInMainHand().getType())) {

                if (NBT.checkNBT(damager.getItemInMainHand(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()))) {
                        action(victim, e.getDamager(), NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()), e.getFinalDamage());
                    }
                }
            }
        }
    }

    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 90) == 5) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 70) == 5) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 50) == 5) {
            return true;
        } else if (lvl == 99) {
            return true;
        }
        return false;
    }

    private static void action(LittlePlayer pl, Entity damager, int lvl, double damage) {
        AtomicInteger countOfDamage = new AtomicInteger(0);
        switch (lvl) {
            case 1 -> { countOfDamage.set(1); }
            case 2 -> { countOfDamage.set(2); }
            case 3 -> { countOfDamage.set(3); }
        }
        AtomicInteger i = new AtomicInteger(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                switch (i.get()) {
                    case 0 -> pl.damage(damage, damager);
                    case 1 -> pl.damage(damage / 1.5, damager);
                    case 2 -> pl.damage(damage / 2, damager);
                }
                i.set(i.get() + 1);
                if (i.get() == countOfDamage.get()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 15L, 15L);
    }

}

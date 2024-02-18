package me.d1lta.prison.enchants.enchantments.sword;

import java.util.Random;
import me.d1lta.prison.Main;
import me.d1lta.prison.enchants.book.BoxerBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Boxer implements Listener {

    static Enchantments ench = Enchantments.BOXER;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (CheckUtils.checkForPlayer(e.getEntity(), e.getDamager()) && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer damager = new LittlePlayer(e.getDamager().getUniqueId());
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            if (BoxerBook.applicableTo.contains(damager.getItemInMainHand().getType())) {

                if (NBT.checkNBT(damager.getItemInMainHand(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()))) {
                        action(victim, NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()));
                    }
                }
            }
        }
    }

    private static void action(LittlePlayer pl, int lvl) {
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            switch (lvl) {
                case 1 -> pl.addVelocity(new Vector(0, 0.5, 0));
                case 2 -> pl.addVelocity(new Vector(0, 1, 0));
                case 3 -> pl.addVelocity(new Vector(0, 1.5, 0));
            }
        }, 5L);
    }

    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 140) == 5) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 90) == 5) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 70) == 5) {
            return true;
        } else if (lvl == 99) {
            return true;
        }
        return false;
    }

}

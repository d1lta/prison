package me.d1lta.prison.enchants.enchantments.sword;

import java.util.Random;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.enchants.book.VampirismBook;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Vampirism implements Listener {

    static Enchantments ench = Enchantments.VAMPIRISM;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (CheckUtils.checkForPlayer(e.getEntity()) && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer damager = new LittlePlayer(e.getDamager().getUniqueId());
            if (VampirismBook.applicableTo.contains(damager.getItemInMainHand().getType())) {

                if (NBT.checkNBT(damager.getItemInMainHand(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()))) {
                        damager.addHP(restoreHP(NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()), e.getDamage()));
                    }
                }
            }
        }
    }

    private double restoreHP(int lvl, double damage) {
        switch (lvl) {
            case 1 -> { return damage * 0.05; }
            case 2 -> { return damage * 0.1; }
            case 3 -> { return damage * 0.15; }
            default -> { return 0; }
        }
    }

    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 10) == 2) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 7) == 2) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 5) == 2) {
            return true;
        }
        return false;
    }

}

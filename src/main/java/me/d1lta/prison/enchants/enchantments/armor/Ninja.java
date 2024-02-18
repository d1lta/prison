package me.d1lta.prison.enchants.enchantments.armor;

import java.util.Random;
import me.d1lta.prison.enchants.book.NinjaBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Ninja implements Listener {

    static Enchantments ench = Enchantments.NINJA;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
            return;
        }
        if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) {
            return;
        }
        LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
        if (NinjaBook.applicableTo.contains(victim.getBoots().getType())) {

            if (NBT.checkNBT(victim.getBoots(), ench.getName(), 0)) {

                if (chance(NBT.getIntNBT(victim.getBoots(), ench.getName()))) {
                    action(victim, NBT.getIntNBT(victim.getBoots(), ench.getName()));
                }
            }
        }
    }

    private static void action(LittlePlayer pl, int lvl) {
        switch (lvl) {
            case 1 -> pl.addEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
            case 2 -> pl.addEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
            case 3 -> pl.addEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
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

}

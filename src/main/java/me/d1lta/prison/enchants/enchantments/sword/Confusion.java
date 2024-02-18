package me.d1lta.prison.enchants.enchantments.sword;

import java.util.Random;
import me.d1lta.prison.enchants.book.ConfusionBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Confusion implements Listener {

    static Enchantments ench = Enchantments.CONFUSION;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (CheckUtils.checkForPlayer(e.getEntity(), e.getDamager()) && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer damager = new LittlePlayer(e.getDamager().getUniqueId());
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            if (ConfusionBook.applicableTo.contains(damager.getItemInMainHand().getType()))
            {
                if (NBT.checkNBT(damager.getItemInMainHand(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()))) {
                        action(victim, NBT.getIntNBT(damager.getItemInMainHand(), ench.getName()));
                    }
                }
            }
        }
    }

    private static void action(LittlePlayer pl, int lvl) {
        switch (lvl) {
            case 1 -> pl.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 0));
            case 2 -> pl.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 90, 0));
            case 3 -> pl.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 110, 0));
        }
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

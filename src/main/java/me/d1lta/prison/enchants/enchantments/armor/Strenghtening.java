package me.d1lta.prison.enchants.enchantments.armor;

import java.util.Random;
import me.d1lta.prison.enchants.book.StrenghteningBook;
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

public class Strenghtening implements Listener {

    static Enchantments ench = Enchantments.STRENGHTENING;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player && e.getDamager() instanceof Player && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            if (StrenghteningBook.applicableTo.contains(victim.getChestplate().getType())) {

                if (NBT.checkNBT(victim.getChestplate(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(victim.getChestplate(), ench.getName()))) {
                        action(victim, NBT.getIntNBT(victim.getChestplate(), ench.getName()));
                    }
                }
            }
        }
    }

    private static void action(LittlePlayer pl, int lvl) {
        switch (lvl) {
            case 1 -> pl.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0));
            case 2 -> pl.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0));
            case 3 -> pl.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 1));
        }
    }

    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 140) == 5) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 100) == 5) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 70) == 5) {
            return true;
        } else if (lvl == 99) {
            return true;
        }
        return false;
    }

}

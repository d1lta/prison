package me.d1lta.prison.enchants.enchantments.armor;

import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

public class Ninja implements Enchantment, Listener {

    static Enchantments ench = Enchantments.NINJA;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getEntity().getUniqueId()).getBoots(), 90, 70, 50)) {
            action(null, new LittlePlayer(e.getEntity().getUniqueId()), null, getLVL(new LittlePlayer(e.getEntity().getUniqueId()).getBoots(), ench), 0);
        }
    }

    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl, double damage) {
        switch (lvl) {
            case 1 -> summoner.addEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
            case 2 -> summoner.addEffect(new PotionEffect(PotionEffectType.SPEED, 60, 0));
            case 3 -> summoner.addEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
        }
    }
}

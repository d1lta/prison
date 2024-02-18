package me.d1lta.prison.enchants.enchantments.sword;

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

public class Blindness implements Enchantment, Listener {

    static Enchantments ench = Enchantments.BLINDNESS;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), 140, 90, 70)) {
            action(null, null, new LittlePlayer(e.getEntity().getUniqueId()), getLVL(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), ench), 0);
        }
    }

    @Override
    public void action(@Nullable Location location, @Nullable LittlePlayer summoner, LittlePlayer victim, int lvl, double damage) {
        switch (lvl) {
            case 1 -> victim.addEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
            case 2 -> victim.addEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));
            case 3 -> victim.addEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0));
        }
    }
}

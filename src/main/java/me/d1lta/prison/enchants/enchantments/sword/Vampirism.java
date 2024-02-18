package me.d1lta.prison.enchants.enchantments.sword;

import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Nullable;

public class Vampirism implements Enchantment, Listener {

    static Enchantments ench = Enchantments.VAMPIRISM;

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), 15, 10, 8)) {
            action(null, new LittlePlayer(e.getDamager().getUniqueId()), null, getLVL(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), ench), e.getFinalDamage());
        }
    }

    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl, double damage) {
        switch (lvl) {
            case 1 -> summoner.addHP(damage * 0.05);
            case 2 -> summoner.addHP(damage * 0.1);
            case 3 -> summoner.addHP(damage * 0.15);
            default -> summoner.addHP(0.0);
        }
    }
}

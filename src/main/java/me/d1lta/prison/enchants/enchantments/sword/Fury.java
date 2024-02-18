package me.d1lta.prison.enchants.enchantments.sword;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.Nullable;

public class Fury implements Enchantment, Listener {

    static Enchantments ench = Enchantments.FURY;
    Map<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        if (checkForAction(ench, new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), 140, 90, 70)) {
            action(null, new LittlePlayer(e.getDamager().getUniqueId()), new LittlePlayer(e.getEntity().getUniqueId()),
                    getLVL(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand(), ench), e.getFinalDamage());
        }
    }


    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, LittlePlayer victim, int lvl, double damage) {
        assert summoner != null;
        if (cooldown.containsKey(summoner.uuid)) {
            if (cooldown.get(summoner.uuid) - System.currentTimeMillis() >= 3) { return; }
            else { cooldown.remove(summoner.uuid); }
        }
        switch (lvl) {
            case 1 -> victim.damage(damage / 2.0, summoner.castToEntity());
            case 2 -> victim.damage(damage / 1.5, summoner.castToEntity());
            case 3 -> victim.damage(damage, summoner.castToEntity());
        }
        cooldown.put(summoner.uuid, System.currentTimeMillis());
    }
}

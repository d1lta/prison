package me.d1lta.prison.events;


import java.util.List;
import java.util.Random;
import me.d1lta.prison.entities.bosses.Vindicator;
import me.d1lta.prison.enums.TrainerSkills;
import me.d1lta.prison.items.AdminStick;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

    public static List<String> disabledPVP = List.of("hub");

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (CheckUtils.checkForPlayer(e.getDamager())) {
            if (e.getEntity().name().equals(Vindicator.name)) { // Vindicator
                LittlePlayer pl = new LittlePlayer(e.getDamager().getUniqueId());
                if (Vindicator.damageDealers.get(pl.uuid) != null) {
                    Vindicator.damageDealers.put(pl.uuid, Vindicator.damageDealers.get(pl.uuid) + e.getFinalDamage());
                } else {
                    Vindicator.damageDealers.put(pl.uuid, e.getFinalDamage());
                }
                if (pl.getItemInMainHand().isSimilar(AdminStick.getStick())) { // AdminStick
                    if (!CheckUtils.checkForPlayer(e.getDamager())) { return; }
                    if (!CheckUtils.checkForNull(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand())) { return; }
                    if (NBT.getKeys(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand()).contains("adminstick")) {
                        e.getEntity().remove();
                    }
                }
            }
        }
        if (CheckUtils.checkForPlayer(e.getEntity())) {
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            if (TrainerSkills.getSkills(victim.getAgilityLvl()).getAgility_boost() > new Random().nextFloat(1, 101)) {
                victim.sendMessage(DComponent.create("Вы уклонились!")); // Уклонение
                e.setCancelled(true);
            }
        }
        if (CheckUtils.checkForPlayer(e.getEntity(), e.getDamager())) {
            LittlePlayer victim = new LittlePlayer(e.getEntity().getUniqueId());
            LittlePlayer damager = new LittlePlayer(e.getDamager().getUniqueId());

            if (disabledPVP.contains(victim.getWorld().getName())) { // pvp disabler
                e.setCancelled(true);
            } else {
                int armor = (int) victim.getAttribute(Attribute.GENERIC_ARMOR).getValue();
                if (armor < 4) {
                    armor = 4;
                }
                if (e.getFinalDamage() / armor * 2.75 * TrainerSkills.getSkills(damager.getStrengthLvl()).getStrength_boost() > 0.0) {
                    victim.damage(e.getFinalDamage() / armor * 2.75 * TrainerSkills.getSkills(damager.getStrengthLvl()).getStrength_boost());
                }
            }
            if (victim.getFaction().equals(damager.getFaction())) { // Фракционный пвп
                e.setCancelled(true);
            }
        }

    }
}

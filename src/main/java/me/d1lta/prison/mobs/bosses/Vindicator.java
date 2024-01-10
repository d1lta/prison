package me.d1lta.prison.mobs.bosses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Vindicator implements Listener {

    public static Component vindicatorName = ComponentUtils.component("Виндикатор", TextColor.color(200,60,60));
    private final HashMap<UUID, Double> dealDamagers = new HashMap<>();
    private static final double multiplier = 2.5;

    public Vindicator(Location location) {
        if (location == null) {
            return;
        }
        org.bukkit.entity.Vindicator vindicator = (org.bukkit.entity.Vindicator) location.getWorld().spawnEntity(location, EntityType.VINDICATOR);
        vindicator.customName(vindicatorName);
        vindicator.setCustomNameVisible(true);
        vindicator.setAggressive(true);
        AttributeInstance attribute = vindicator.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(800);
        vindicator.setHealth(800);
        vindicator.setAI(false);
    }

    @EventHandler
    public void onTakeDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType().equals(EntityType.VINDICATOR) && e.getEntity().name().equals(vindicatorName)) {
            if (e.getDamager() instanceof Player) {
                LittlePlayer pl = new LittlePlayer(e.getDamager().getUniqueId());
                if (dealDamagers.containsKey(pl.uuid)) {
                    dealDamagers.put(pl.uuid, dealDamagers.get(pl.uuid) + e.getFinalDamage());
                } else {
                    dealDamagers.put(pl.uuid, e.getFinalDamage());
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getType().equals(EntityType.VINDICATOR) && e.getEntity().name().equals(vindicatorName)) {
            List<UUID> keys = dealDamagers.entrySet().stream().sorted(Entry.<UUID, Double>comparingByValue().reversed()).limit(3).map(Entry::getKey).toList();
            Bukkit.getOnlinePlayers().forEach(pl -> {
                pl.sendMessage("Виндикатор повержен!");
                pl.sendMessage("");
                keys.forEach(it -> {
                    pl.sendMessage(keys.indexOf(it) + 1 + ". " + new LittlePlayer(it).getName() + " - " + ((int) Double.parseDouble(dealDamagers.get(it).toString())) + " урона");
                });
                if (dealDamagers.containsKey(pl.getUniqueId())) {
                    pl.sendMessage("");
                    pl.sendMessage("Вы заработали " + (int) (dealDamagers.get(pl.getUniqueId()) * multiplier) + "$");
                }
            });
            for (UUID uuid : dealDamagers.keySet()) {
                new LittlePlayer(uuid).addMoney(dealDamagers.get(uuid) * multiplier, "boss");
            }
            e.getDrops().clear();
            e.setDroppedExp(0);
            dealDamagers.clear();
        }
    }

}

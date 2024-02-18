package me.d1lta.prison.mobs;

import me.d1lta.prison.Main;
import me.d1lta.prison.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.scheduler.BukkitRunnable;

public class Rat {

    public static Component ratName = ComponentUtils.component("Тюремная крыса", TextColor.color(200,60,60));

    public Rat(Location location) {
        Silverfish silverfish = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);
        silverfish.customName(ratName);
        silverfish.setCustomNameVisible(true);

        AttributeInstance attribute = silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(10);
        silverfish.setHealth(10);
        silverfish.setAI(false);


        new BukkitRunnable() {
            @Override
            public void run() {
                if (silverfish.getHealth() != 10) {
                    silverfish.setAI(true);
                }
            }
        }.runTaskTimer(Main.plugin, 5L, 5L);

    }

}

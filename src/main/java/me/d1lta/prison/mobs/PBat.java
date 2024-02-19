package me.d1lta.prison.mobs;

import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;

public class PBat {

    public static net.kyori.adventure.text.Component batName = DComponent.create("Летучая мышь", TextColor.color(200,60,60));

    public PBat(Location location) {
        Bat bat = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
        bat.customName(batName);
        bat.setCustomNameVisible(true);

        AttributeInstance attribute = bat.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(20);
        bat.setHealth(20);
        bat.setAI(false);
    }

}

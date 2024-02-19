package me.d1lta.prison.mobs;

import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class PZombie {

    public static net.kyori.adventure.text.Component zombieName = DComponent.create("Зомби", TextColor.color(200,60,60));

    public PZombie(Location location) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.customName(zombieName);
        zombie.setCustomNameVisible(true);

        AttributeInstance attribute = zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attribute.setBaseValue(20);
        zombie.setHealth(20);
        zombie.setAI(false);
    }

}

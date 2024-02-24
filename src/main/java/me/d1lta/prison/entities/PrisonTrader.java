package me.d1lta.prison.entities;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public abstract class PrisonTrader {

    public PrisonTrader(Location location, Component name, int health, boolean AI) {
        if (location == null) { return; }
        Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.customName(name);
        villager.setCustomNameVisible(true);
        AttributeInstance attribute = villager.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert attribute != null;
        attribute.setBaseValue(health);
        villager.setHealth(health);
        villager.setAI(AI);
    }
}

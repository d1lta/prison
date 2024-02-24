package me.d1lta.prison.entities;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;

public abstract class PrisonMob {

    public PrisonMob(String monsterType, Location location, Component name, int health, boolean AI) {
        if (location == null) { return; }
        Monster monster = getMonster(monsterType, location);
        monster.customName(name);
        monster.setCustomNameVisible(true);
        AttributeInstance attribute = monster.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert attribute != null;
        attribute.setBaseValue(health);
        monster.setHealth(health);
        monster.setAI(AI);
    }

    @NotNull
    private Monster getMonster(String monsterType, Location location) {
        EntityType entityType;
        switch (monsterType.toLowerCase()) {
            case "silverfish" -> entityType = EntityType.SILVERFISH;
            case "bat" -> entityType = EntityType.BAT;
            case "zombie" -> entityType = EntityType.ZOMBIE;

            case "vindicator" -> entityType = EntityType.VINDICATOR;
            default -> entityType = EntityType.ZOGLIN;
        }
        return (Monster) location.getWorld().spawnEntity(location, entityType);
    }

}

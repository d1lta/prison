package me.d1lta.prison.entities.bosses;

import java.util.HashMap;
import java.util.UUID;
import me.d1lta.prison.entities.PrisonMob;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;

public class Vindicator extends PrisonMob {

    public static Component name = DComponent.create("Виндикатор", TextColor.color(200,60,60));
    public static HashMap<UUID, Double> damageDealers = new HashMap<>();
    public static final double multiplier = 2.5;

    public Vindicator(Location location) {
        super("vindicator", location, name, 800, true);
    }
}

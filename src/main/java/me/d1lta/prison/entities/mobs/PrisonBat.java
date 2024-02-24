package me.d1lta.prison.entities.mobs;

import me.d1lta.prison.entities.PrisonMob;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;

public class PrisonBat extends PrisonMob {

    public static final Component name = DComponent.create("Летучая мышь", TextColor.color(200,60,60));

    public PrisonBat(Location location) {
        super("bat", location, name, 20, true);
    }
}

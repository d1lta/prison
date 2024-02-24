package me.d1lta.prison.entities.mobs;

import me.d1lta.prison.entities.PrisonMob;
import me.d1lta.prison.utils.DComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;

public class PrisonRat extends PrisonMob implements Listener {

    public static final Component name = DComponent.create("Тюремная крыса", TextColor.color(200,60,60));

    public PrisonRat(Location location) {
        super("silverfish", location, name,  10, false);
    }
}

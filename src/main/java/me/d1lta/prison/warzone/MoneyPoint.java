package me.d1lta.prison.warzone;

import me.d1lta.prison.enums.Factions;
import org.bukkit.Bukkit;

public class MoneyPoint extends Point {

    public static final double modifier = 1.2;

    public MoneyPoint() {
        super(() -> {
            Bukkit.broadcastMessage("money point bonus available for " + WarzoneCapture.money.capturedBy.getName());
        });
        this.type = "money";
    }

}

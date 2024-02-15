package me.d1lta.prison.warzone;

import org.bukkit.Bukkit;

public class BlocksPoint extends Point {

    public static final double multiplier = 1.2;

    public BlocksPoint() {
        super(() -> {
            Bukkit.broadcastMessage("blocks point bonus available for " + WarzoneCapture.blocks.capturedBy.getName());
        });
        this.type = "blocks";
    }
}

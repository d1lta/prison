package me.d1lta.prison.warzone;

import java.util.concurrent.Callable;
import me.d1lta.prison.enums.Factions;
import org.bukkit.Bukkit;

public class BlocksPoint extends Point {

    public BlocksPoint() {
        super(() -> {
            Bukkit.broadcastMessage("blocks point bonus available for " + WarzoneCapture.blocks.capturedBy.getName());
        });
        this.type = "blocks";
    }
}

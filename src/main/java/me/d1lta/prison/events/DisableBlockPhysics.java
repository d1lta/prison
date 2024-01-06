package me.d1lta.prison.events;

import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;

public class DisableBlockPhysics implements Listener {

    List<String> disabledWorlds = List.of(
            "hub", "concrete", "icehills"
    );

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        disabledWorlds.forEach(it -> {
            if (e.getBlock().getLocation().getWorld().getName().equals(it)) {
                e.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onMelt(BlockFadeEvent e) {
        disabledWorlds.forEach(it -> {
            if (e.getBlock().getLocation().getWorld().getName().equals(it)) {
                e.setCancelled(true);
            }
        });
    }

}

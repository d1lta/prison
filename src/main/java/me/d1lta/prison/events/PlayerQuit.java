package me.d1lta.prison.events;

import me.d1lta.prison.PlayerValues;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        PlayerValues.save(pl.uuid);
        AutoSell.uuids.remove(pl.uuid);
    }

}

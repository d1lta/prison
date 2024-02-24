package me.d1lta.prison.events;

import static me.d1lta.prison.warzone.WarzoneCapture.blocks;
import static me.d1lta.prison.warzone.WarzoneCapture.getPointType;
import static me.d1lta.prison.warzone.WarzoneCapture.haste;
import static me.d1lta.prison.warzone.WarzoneCapture.logic;
import static me.d1lta.prison.warzone.WarzoneCapture.money;
import static me.d1lta.prison.warzone.WarzoneCapture.speed;

import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) { // TODO: убери этот ивент и какойнить таймер намути
        if (e.getPlayer().getLocation().getWorld().getName().equals("warzone")) {
            LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
            String point = getPointType(pl.getLocation());
            if (point == null) {
                point = "null";
            }
            if (point.equals(haste.type)) {
                logic(pl, haste);
            }
            if (point.equals(money.type)) {
                logic(pl, money);
            }
            if (point.equals(blocks.type)) {
                logic(pl, blocks);
            }
            if (point.equals(speed.type)) {
                logic(pl, speed);
            }
        }
    }

}

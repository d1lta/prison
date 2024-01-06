package me.d1lta.prison.events;

import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".money") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".money", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.dirt") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.dirt", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.gravel") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.gravel", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.sand") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.sand", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.stone") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.stone", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.coal_ore") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.coal_ore", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.iron_ore") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.iron_ore", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.gold_ore") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.gold_ore", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.light_gray_concrete_powder") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.light_gray_concrete_powder", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.white_concrete_powder") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.white_concrete_powder", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.netherrack") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.netherrack", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.soul_sand") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.soul_sand", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.nether_quartz_ore") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.nether_quartz_ore", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.sandstone") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.sandstone", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.quartz_block") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.quartz_block", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.prismarine_bricks") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.prismarine_bricks", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.purpur_block") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.purpur_block", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.end_stone_bricks") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.end_stone_bricks", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.white_wool") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.white_wool", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.cobweb") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.cobweb", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.terracotta") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.terracotta", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.brown_glazed_terracotta") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.brown_glazed_terracotta", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.ice") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.ice", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.packed_ice") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.packed_ice", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.purple_terracotta") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.purple_terracotta", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".blocks.obsidian") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".blocks.obsidian", "0");
        }
        if (Jedis.get(e.getPlayer().getUniqueId() + ".lvl") == null) {
            Jedis.set(e.getPlayer().getUniqueId() + ".lvl", "0");
        }
        Bukkit.getWorlds().forEach(it -> {
            if (it.getName().equals("hub")) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> e.getPlayer().teleport(new Location(Bukkit.getWorld("hub"),0.5,65,0.5)), 1L);
            }
        });

    }

}

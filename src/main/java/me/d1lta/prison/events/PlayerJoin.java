package me.d1lta.prison.events;

import java.util.UUID;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.PlayerValues;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.enums.LevelBoosts;
import me.d1lta.prison.PrisonEvents;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (Jedis.get(uuid + ".blocks") == null) {
            Jedis.set(uuid + ".blocks", "0");
        }
        if (Jedis.get(uuid + ".money") == null) {
            Jedis.set(uuid + ".money", "0");
        }
        if (Jedis.get(uuid + ".rats") == null) {
            Jedis.set(uuid + ".rats", "0");
        }
        if (Jedis.get(uuid + ".toilet") == null) {
            Jedis.set(uuid + ".toilet", "false");
        }
        if (Jedis.get(uuid + ".toilet_chance_max") == null) {
            Jedis.set(uuid + ".toilet_chance_max", "4000");
        }
        if (Jedis.get(uuid + ".toilet_chance") == null) {
            Jedis.set(uuid + ".toilet_chance", "4000");
        }
        if (Jedis.get(uuid + ".shower") == null) {
            Jedis.set(uuid + ".shower", "false");
        }
        if (Jedis.get(uuid + ".shower_chance_max") == null) {
            Jedis.set(uuid + ".shower_chance_max", "4000");
        }
        if (Jedis.get(uuid + ".shower_chance") == null) {
            Jedis.set(uuid + ".shower_chance", "4000");
        }
        if (Jedis.get(uuid + ".sleep") == null) {
            Jedis.set(uuid + ".sleep", "false");
        }
        if (Jedis.get(uuid + ".sleep_chance_max") == null) {
            Jedis.set(uuid + ".sleep_chance_max", "4000");
        }
        if (Jedis.get(uuid + ".sleep_chance") == null) {
            Jedis.set(uuid + ".sleep_chance", "4000");
        }
        if (Jedis.get(uuid + ".kills") == null) {
            Jedis.set(uuid + ".kills", "0");
        }
        if (Jedis.get(uuid + ".deaths") == null) {
            Jedis.set(uuid + ".deaths", "0");
        }
        if (Jedis.get(uuid + ".bat") == null) {
            Jedis.set(uuid + ".bat", "0");
        }
        if (Jedis.get(uuid + ".zombie") == null) {
            Jedis.set(uuid + ".zombie", "0");
        }
        if (Jedis.get(uuid + ".strength") == null) {
            Jedis.set(uuid + ".strength", "0");
        }
        if (Jedis.get(uuid + ".agility") == null) {
            Jedis.set(uuid + ".agility", "0");
        }
        if (Jedis.get(uuid + ".needs") == null) {
            Jedis.set(uuid + ".needs", "0");
        }
        if (Jedis.get(uuid + ".faction") == null) {
            Jedis.set(uuid + ".faction", Factions.NO_FACTION.getName());
        }
        if (Jedis.get(uuid + ".vault") == null) {
            Jedis.set(uuid + ".vault", "false");
        }
        if (Jedis.get(uuid + ".blocks.dirt") == null) {
            Jedis.set(uuid + ".blocks.dirt", "0");
        }
        if (Jedis.get(uuid + ".blocks.gravel") == null) {
            Jedis.set(uuid + ".blocks.gravel", "0");
        }
        if (Jedis.get(uuid + ".blocks.sand") == null) {
            Jedis.set(uuid + ".blocks.sand", "0");
        }
        if (Jedis.get(uuid + ".blocks.stone") == null) {
            Jedis.set(uuid + ".blocks.stone", "0");
        }
        if (Jedis.get(uuid + ".blocks.coal_ore") == null) {
            Jedis.set(uuid + ".blocks.coal_ore", "0");
        }
        if (Jedis.get(uuid + ".blocks.iron_ore") == null) {
            Jedis.set(uuid + ".blocks.iron_ore", "0");
        }
        if (Jedis.get(uuid + ".blocks.gold_ore") == null) {
            Jedis.set(uuid + ".blocks.gold_ore", "0");
        }
        if (Jedis.get(uuid + ".blocks.light_gray_concrete_powder") == null) {
            Jedis.set(uuid + ".blocks.light_gray_concrete_powder", "0");
        }
        if (Jedis.get(uuid + ".blocks.white_concrete_powder") == null) {
            Jedis.set(uuid + ".blocks.white_concrete_powder", "0");
        }
        if (Jedis.get(uuid + ".blocks.netherrack") == null) {
            Jedis.set(uuid + ".blocks.netherrack", "0");
        }
        if (Jedis.get(uuid + ".blocks.soul_sand") == null) {
            Jedis.set(uuid + ".blocks.soul_sand", "0");
        }
        if (Jedis.get(uuid + ".blocks.nether_quartz_ore") == null) {
            Jedis.set(uuid + ".blocks.nether_quartz_ore", "0");
        }
        if (Jedis.get(uuid + ".blocks.sandstone") == null) {
            Jedis.set(uuid + ".blocks.sandstone", "0");
        }
        if (Jedis.get(uuid + ".blocks.red_sandstone") == null) {
            Jedis.set(uuid + ".blocks.red_sandstone", "0");
        }
        if (Jedis.get(uuid + ".blocks.quartz_block") == null) {
            Jedis.set(uuid + ".blocks.quartz_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.prismarine") == null) {
            Jedis.set(uuid + ".blocks.prismarine", "0");
        }
        if (Jedis.get(uuid + ".blocks.prismarine_bricks") == null) {
            Jedis.set(uuid + ".blocks.prismarine_bricks", "0");
        }
        if (Jedis.get(uuid + ".blocks.purpur_block") == null) {
            Jedis.set(uuid + ".blocks.purpur_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.purpur_pillar") == null) {
            Jedis.set(uuid + ".blocks.purpur_pillar", "0");
        }
        if (Jedis.get(uuid + ".blocks.end_stone_bricks") == null) {
            Jedis.set(uuid + ".blocks.end_stone_bricks", "0");
        }
        if (Jedis.get(uuid + ".blocks.white_wool") == null) {
            Jedis.set(uuid + ".blocks.white_wool", "0");
        }
        if (Jedis.get(uuid + ".blocks.cobweb") == null) {
            Jedis.set(uuid + ".blocks.cobweb", "0");
        }
        if (Jedis.get(uuid + ".blocks.terracotta") == null) {
            Jedis.set(uuid + ".blocks.terracotta", "0");
        }
        if (Jedis.get(uuid + ".blocks.brown_glazed_terracotta") == null) {
            Jedis.set(uuid + ".blocks.brown_glazed_terracotta", "0");
        }
        if (Jedis.get(uuid + ".blocks.ice") == null) {
            Jedis.set(uuid + ".blocks.ice", "0");
        }
        if (Jedis.get(uuid + ".blocks.packed_ice") == null) {
            Jedis.set(uuid + ".blocks.packed_ice", "0");
        }
        if (Jedis.get(uuid + ".blocks.purple_terracotta") == null) {
            Jedis.set(uuid + ".blocks.purple_terracotta", "0");
        }
        if (Jedis.get(uuid + ".blocks.obsidian") == null) {
            Jedis.set(uuid + ".blocks.obsidian", "0");
        }
        if (Jedis.get(uuid + ".blocks.lapis_block") == null) {
            Jedis.set(uuid + ".blocks.lapis_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.iron_block") == null) {
            Jedis.set(uuid + ".blocks.iron_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.gold_block") == null) {
            Jedis.set(uuid + ".blocks.gold_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.diamond_block") == null) {
            Jedis.set(uuid + ".blocks.diamond_block", "0");
        }
        if (Jedis.get(uuid + ".blocks.oak_wood") == null) {
            Jedis.set(uuid + ".blocks.oak_wood", "0");
        }
        if (Jedis.get(uuid + ".skills.rat") == null) {
            Jedis.set(uuid + ".skills.rat", "0");
        }
        if (Jedis.get(uuid + ".skills.armor") == null) {
            Jedis.set(uuid + ".skills.armor", "0");
        }
        if (Jedis.get(uuid + ".skills.potion") == null) {
            Jedis.set(uuid + ".skills.potion", "0");
        }
        if (Jedis.get(uuid + ".skills.key") == null) {
            Jedis.set(uuid + ".skills.key", "0");
        }
        if (Jedis.get(uuid + ".lvl") == null) {
            Jedis.set(uuid + ".lvl", "0");
        }
        if (Jedis.get(uuid + ".autosell") == null) {
            Jedis.set(uuid + ".autosell", "false");
        }
        if (Jedis.get(uuid + ".autosell").equals("true")) {
            AutoSell.uuids.add(uuid);
        }
        LevelBoosts.get(new LittlePlayer(uuid).getLevel()).applyToPlayer(new LittlePlayer(uuid));
        new PrisonEvents().applyEvents(new LittlePlayer(uuid));

        PlayerValues.setValues(uuid);

        Bukkit.getWorlds().forEach(it -> {
            if (it.getName().equals("hub")) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, () -> e.getPlayer().teleport(LocationUtils.spawnPoint("hub")), 1L);
            }
        });

    }

}

package me.d1lta.prison.enchants.enchantments.instruments;

import static me.d1lta.prison.events.BlockBreak.simulate;

import java.util.Random;
import javax.annotation.Nullable;
import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Hammer implements Enchantment, Listener {

    static Enchantments ench = Enchantments.HAMMER;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (checkForAction(ench, e.getPlayer().getInventory().getItemInMainHand(), 300, 200 ,100)) {
            action(e.getBlock().getLocation(), new LittlePlayer(e.getPlayer().getUniqueId()), null, getLVL(e.getPlayer().getInventory().getItemInMainHand(), ench), 0);
        }
    }

    @Override
    public void action(Location loc, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl,
            double damage) {
        int radX = 0;
        int radZ = 0;
        if (lvl == 1) {
            radX = 1;
            radZ = 1;
        } else if (lvl == 2) {
            if (new Random().nextInt(1, 6) < 3) {
                radX = 2;
                radZ = 1;
            } else {
                radX = 1;
                radZ = 2;
            }
        } else if (lvl == 3) {
            if (new Random().nextInt(1, 50) < 3) {
                radX = 2;
                radZ = 2;
            } else {
                radX = 1;
                radZ = 1;
            }
        } else if (lvl == 99) {
            radX = 2;
            radZ = 2;
        }
        for (int x = loc.getBlockX() - radX; x <= loc.getBlockX() + radZ; x++) {
            for (int z = loc.getBlockZ() - radX; z <= loc.getBlockZ() + radZ; z++) {
                Location location = new Location(loc.getWorld(), x, loc.getBlockY(), z);
                if (loc.equals(location)) {
                    continue;
                }
                if (MineUtils.isAllowedToBreakBlock(location)) {
                    if (!AllowedBlocks.blockMats.contains(location.getBlock().getType())) {
                        continue;
                    }
                    summoner.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
                    simulate(summoner, location);
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
    }
}

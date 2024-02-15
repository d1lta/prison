package me.d1lta.prison.enchants.enchantments;

import java.util.List;
import java.util.Random;
import me.d1lta.prison.events.BlockBreak;
import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.MineUtils;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Hammer implements Listener {

    public static List<Material> applicableTo = List.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.WOODEN_SHOVEL,
            Material.STONE_SHOVEL,
            Material.IRON_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.SHEARS
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (applicableTo.contains(pl.getItemInMainHand().getType())) {
            if (NBT.getIntNBT(pl.getItemInMainHand(), "hammer") != 0) {
                if (chance(NBT.getIntNBT(pl.getItemInMainHand(), "hammer"))) {
                    hammer(e.getBlock().getLocation(), pl, NBT.getIntNBT(pl.getItemInMainHand(), "hammer"));
                }
            }
        }
    }

    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 300) == 5) { // 375
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 200) == 5) { // 315
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 100) == 5) { //250
            return true;
        }
        return false;
    }

    private void hammer(Location loc, LittlePlayer pl, int lvl) {
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
        }
        for (int x = loc.getBlockX() - radX; x <= loc.getBlockX() + radZ; x++) {
            for (int z = loc.getBlockZ() - radX; z <= loc.getBlockZ() + radZ; z++) {
                Location location = new Location(loc.getWorld(), x, loc.getBlockY(), z);
                if (loc.equals(location)) {
                    continue;
                }
                if (MineUtils.isAllowedToBreakBlock(location)) {
                    if (!AllowedBlocks.blocks.stream().map(ItemStack::getType).toList().contains(location.getBlock().getType())) {
                        continue;
                    }
                    pl.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1L, 1L);
                    BlockBreak.simulate(pl, location);
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
    }

}

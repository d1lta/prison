package me.d1lta.prison.enchants.enchantments.armor;

import static me.d1lta.prison.utils.CheckUtils.checkForNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Sacred implements Listener {

    static Enchantments ench = Enchantments.SACRED;

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            if (new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            LittlePlayer pl = new LittlePlayer(e.getEntity().getUniqueId());
            List<Integer> counts = sacredCount(pl);
            if (counts.size() != 0) {
                double amount = 0.0;
                double count = 0.0;
                for (Integer i : counts) {
                    if (chance(i, counts.size())) {
                        count += 1.0;
                        amount += restoreHP(i);
                    }
                }
                if (count != 0.0) {
                    pl.addHP(amount / count);
                }
            }
        }
    }

    private static List<Integer> sacredCount(LittlePlayer pl) {
        List<Integer> list = new ArrayList<>();
        if (checkForNull(pl.getHelmet()) && NBT.checkNBT(pl.getHelmet(), ench.getName(), 0)) { list.add(NBT.getIntNBT(pl.getHelmet(), ench.getName())); }
        if (checkForNull(pl.getChestplate()) && NBT.checkNBT(pl.getChestplate(), ench.getName(), 0)) { list.add(NBT.getIntNBT(pl.getChestplate(), ench.getName())); }
        if (checkForNull(pl.getLeggings()) && NBT.checkNBT(pl.getLeggings(), ench.getName(), 0)) { list.add(NBT.getIntNBT(pl.getLeggings(), ench.getName())); }
        if (checkForNull(pl.getBoots()) && NBT.checkNBT(pl.getBoots(), ench.getName(), 0)) { list.add(NBT.getIntNBT(pl.getBoots(), ench.getName())); }
        return list;
    }

    private double restoreHP(int lvl) {
        switch (lvl) {
            case 1 -> { return 1; }
            case 2 -> { return 1.5; }
            case 3 -> { return 2.5; }
            default -> { return 0; }
        }
    }

    private boolean chance(int lvl, int count) {
        count += 2;
        if (lvl == 1 && new Random().nextInt(1, 80 * (count / 2)) == 2) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 60 * (count / 2)) == 2) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 40 * (count / 2)) == 2) {
            return true;
        }
        return false;
    }

}

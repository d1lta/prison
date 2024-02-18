package me.d1lta.prison.enchants.enchantments.armor;

import java.util.Random;
import me.d1lta.prison.enchants.book.FieryBook;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.events.PVPDisabler;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Fiery implements Listener {

    static Enchantments ench = Enchantments.FIERY;

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            LittlePlayer pl = new LittlePlayer(e.getEntity().getUniqueId());
            if (e.getDamager() instanceof Player && new LittlePlayer(e.getDamager().getUniqueId()).getFaction().equals(new LittlePlayer(e.getEntity().getUniqueId()).getFaction())) {
                return;
            }
            if (PVPDisabler.worlds.contains(e.getEntity().getWorld().getName())) { return; }
            if (FieryBook.applicableTo.contains(pl.getLeggings().getType())) {

                if (NBT.checkNBT(pl.getLeggings(), ench.getName(), 0)) {

                    if (chance(NBT.getIntNBT(pl.getLeggings(), ench.getName()))) {
                        e.getDamager().setFireTicks(40);
                    }
                }
            }
        }
    }


    private boolean chance(int lvl) {
        if (lvl == 1 && new Random().nextInt(1, 30) == 2) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, 18) == 2) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, 10) == 2) {
            return true;
        }
        return false;
    }

}

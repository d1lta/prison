package me.d1lta.prison.enchants.enchantments.armor;

import java.util.ArrayList;
import java.util.List;
import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Sacred implements Enchantment, Listener {

    static Enchantments ench = Enchantments.SACRED;

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e) {
        if (!isAllowable(e.getEntity(), e.getDamager(), e.getEntity().getWorld().getName())) { return; }
        List<Integer> lvlProcks = new ArrayList<>();
        if (new LittlePlayer(e.getEntity().getUniqueId()).getArmor().size() == 0) { return; }
        for (ItemStack stack: new LittlePlayer(e.getEntity().getUniqueId()).getArmor()) {
            if (checkForAction(ench, stack, 140, 100, 70)) {
                lvlProcks.add(NBT.getIntNBT(stack, ench.getName()));
            }
        }
        for (Integer i: lvlProcks) {
            action(null, new LittlePlayer(e.getEntity().getUniqueId()), null, i, 0);
        }
    }
    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl, double damage) {
        switch (lvl) {
            case 1 -> summoner.addHP(1);
            case 2 -> summoner.addHP(1.5);
            case 3 -> summoner.addHP(2);
            default -> summoner.addHP(0);
        }
    }
}

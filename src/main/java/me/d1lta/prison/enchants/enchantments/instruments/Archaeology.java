package me.d1lta.prison.enchants.enchantments.instruments;

import me.d1lta.prison.enchants.Enchantment;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Archaeology implements Enchantment, Listener {

    static Enchantments ench = Enchantments.ARCHAEOLOGY;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (checkForAction(ench, e.getPlayer().getInventory().getItemInMainHand(), 150, 100 ,50)) {
            action(null, new LittlePlayer(e.getPlayer().getUniqueId()), getLVL(e.getPlayer().getInventory().getItemInMainHand(), ench));
        }
    }

    @Override
    public void action(Location location, LittlePlayer pl, int lvl) {
        if (pl.getEffects().stream().map(PotionEffect::getType).toList().contains(PotionEffectType.FAST_DIGGING)) {
            pl.removeEffect(PotionEffectType.FAST_DIGGING);
        }
        switch (lvl) {
            case 1 -> pl.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 30, 0));
            case 2 -> pl.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 1));
            case 3 -> pl.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 50, 2));
        }
    }
}

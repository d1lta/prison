package me.d1lta.prison.enchants.enchantments.instruments;

import javax.annotation.Nullable;
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
            action(null, new LittlePlayer(e.getPlayer().getUniqueId()), null, getLVL(e.getPlayer().getInventory().getItemInMainHand(), ench), 0);
        }
    }

    @Override
    public void action(@Nullable Location location, LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl,
            double damage) {
        if (summoner.getEffects().stream().map(PotionEffect::getType).toList().contains(PotionEffectType.FAST_DIGGING)) {
            summoner.removeEffect(PotionEffectType.FAST_DIGGING);
        }
        switch (lvl) {
            case 1 -> summoner.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 30, 0));
            case 2 -> summoner.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 1));
            case 3 -> summoner.addEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 50, 2));
        }
    }
}

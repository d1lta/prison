package me.d1lta.prison.enchants;

import java.util.Random;
import javax.annotation.Nullable;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.events.EntityDamageByEntity;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface Enchantment {

    private boolean chance(int lvl, int chance_lvl1, int chance_lvl2, int chance_lvl3) {
        if (chance_lvl1 == 1 || chance_lvl2 == 1 || chance_lvl3 == 1) {
            return true;
        }
        if (lvl == 1 && new Random().nextInt(1, chance_lvl1) == 1) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, chance_lvl2) == 1) {
            return true;
        } else return lvl == 3 && new Random().nextInt(1, chance_lvl3) == 1;
    }

    default boolean checkForAction(Enchantments ench, ItemStack stack, int chance_lvl1, int chance_lvl2, int chance_lvl3) {
        if (!CheckUtils.checkForNull(stack)) { return false; }
        if (!ench.getApplicableTools().contains(stack.getType())) { return false; }
        if (!NBT.checkNBT(stack, ench.getName(), 0)) { return false; }
        return chance(NBT.getIntNBT(stack, ench.getName()), chance_lvl1, chance_lvl2, chance_lvl3);
    }

    default boolean isAllowable(Entity pl1, Entity pl2, String world) {
        if (!CheckUtils.checkForPlayer(pl1, pl2)) { return false; }
        if (Factions.isPlayersInSingleFaction(new LittlePlayer(pl1.getUniqueId()), new LittlePlayer(pl2.getUniqueId()))) { return false; }
        return !EntityDamageByEntity.disabledPVP.contains(world);
    }


    default int getLVL(ItemStack stack, Enchantments ench) {
        return NBT.getIntNBT(stack, ench.getName());
    }

    void action(@Nullable Location location, @Nullable LittlePlayer summoner, @Nullable LittlePlayer victim, int lvl, double damage);

}

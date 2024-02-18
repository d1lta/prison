package me.d1lta.prison.enchants;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface Enchantment {

    private boolean chance(int lvl, int chance_lvl1, int chance_lvl2, int chance_lvl3) {
        if (lvl == 1 && new Random().nextInt(1, chance_lvl1) == 2) {
            return true;
        } else if (lvl == 2 && new Random().nextInt(1, chance_lvl2) == 2) {
            return true;
        } else if (lvl == 3 && new Random().nextInt(1, chance_lvl3) == 2) {
            return true;
        }
        return false;
    }

    default boolean checkForAction(Enchantments ench, ItemStack stack, int chance_lvl1, int chance_lvl2, int chance_lvl3) {
        if (!CheckUtils.checkForNull(stack)) { return false; }
        if (!ench.getApplicableTools().contains(stack.getType())) { return false; }
        if (!NBT.checkNBT(stack, ench.getName(), 0)) { return false; }
        if (!chance(NBT.getIntNBT(stack, ench.getName()), chance_lvl1, chance_lvl2, chance_lvl3)) { return false; }
        return true;
    }

    default int getLVL(ItemStack stack, Enchantments ench) {
        return NBT.getIntNBT(stack, ench.getName());
    }

    void action(@Nullable Location location, @Nonnull LittlePlayer pl, @Nonnull int lvl);

}

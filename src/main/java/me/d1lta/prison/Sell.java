package me.d1lta.prison;

import me.d1lta.prison.mines.AllowedBlocks;
import me.d1lta.prison.utils.LittlePlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class Sell {

    public static void sell(LittlePlayer pl) {
        double money = 0;
        for (ItemStack i : pl.getInventory()) {
            if (i != null) {
                ItemStack stack = i.clone();
                stack.setAmount(1);
                if (AllowedBlocks.blocks.contains(stack)) {
                    money = money + Main.config.getConfig().getDouble("prices." + stack.getType().name().toLowerCase()) * i.getAmount();
                    i.setAmount(0);
                }
            }
        }


        pl.addMoney(money);
        pl.sendLittleTitle(new TextComponent("Вы продали блоков на " + money + " монет."));
    }

}

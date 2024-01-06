package me.d1lta.prison;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Sell {

    public static void sell(Player pl) {
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
        Jedis.set(pl.getUniqueId() + ".money", String.valueOf(Double.parseDouble(Jedis.get(pl.getUniqueId() + ".money")) + money));
        pl.spigot().sendMessage( ChatMessageType.ACTION_BAR, new TextComponent("Вы продали блоков на " + money + " монет."));
    }

}

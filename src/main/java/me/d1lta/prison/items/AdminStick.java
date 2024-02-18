package me.d1lta.prison.items;

import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminStick implements Listener {

    public static ItemStack getStick() {
        ItemStack apple = new ItemStack(Material.STICK);
        ItemMeta meta = apple.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.displayName(ComponentUtils.component("Убийственная палочка", TextColor.color(214, 144, 0)));
        apple.setItemMeta(meta);
        apple = NBT.addNBT(apple, "adminstick", "1");
        return apple;
    }

    @EventHandler
    public void onEntityHurt(EntityDamageByEntityEvent e) {
        if (!CheckUtils.checkForNull(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand())) { return; }
        if (NBT.getKeys(new LittlePlayer(e.getDamager().getUniqueId()).getItemInMainHand()).contains("adminstick")) {
            e.getEntity().remove();
        }
    }

}

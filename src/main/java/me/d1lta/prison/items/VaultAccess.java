package me.d1lta.prison.items;

import java.util.List;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VaultAccess implements Listener {

    public static ItemStack getAccess() {
        ItemStack access = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = access.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.lore(List.of(ComponentUtils.component("Этот предмет открывает доступ в подвал",
                TextColor.color(0, 214, 199))
        ));
        meta.displayName(ComponentUtils.component("Доступ в подвал", TextColor.color(214, 144, 0)));
        access.setItemMeta(meta);
        return access;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().equals(getAccess())) {
            LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
            if (Jedis.get(e.getPlayer().getUniqueId() + ".vault").equals("false")) {
                e.getPlayer().getInventory().getItemInMainHand().setAmount(0);
                Jedis.set(e.getPlayer().getUniqueId() + ".vault", "true");
                pl.sendMessage("Теперь вы можете попасть в подвал");
            } else {
                e.setCancelled(true);
                pl.dropItem();
                pl.sendMessage("От осознания что у вас уже есть доступ в подвал, вы случайно выронили книгу!");
            }
        }
    }

}

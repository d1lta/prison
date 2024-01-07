package me.d1lta.prison.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LocationUtils;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ArrayList<ItemStack> give = new ArrayList<>();
        for (ItemStack stack : e.getPlayer().getInventory().getContents()) {
            if (stack == null) {
                continue;
            }
            if (Objects.equals(NBT.getStringNBT(stack, "safe"), "true")) {
                e.getDrops().remove(stack);
                give.add(stack);
            }
        }
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            e.getPlayer().spigot().respawn();
            e.getPlayer().teleport(LocationUtils.spawnPoint("hub"));
            give.forEach(it -> e.getPlayer().getInventory().addItem(it));
        }, 2L);
    }
}

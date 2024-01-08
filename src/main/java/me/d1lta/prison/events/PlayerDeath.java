package me.d1lta.prison.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.Main;
import me.d1lta.prison.utils.LocationUtils;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : e.getPlayer().getInventory().getContents()) {
            if (stack == null) {
                continue;
            }
            if (Objects.equals(NBT.getStringNBT(stack, "safe"), "true")) {
                e.getDrops().remove(stack);
                stacks.add(stack);
            }
        }
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            Jedis.set(e.getPlayer().getUniqueId() + ".deaths", String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getUniqueId() + ".deaths")) + 1));
            if (e.getPlayer().getKiller() != null) {
                Jedis.set(e.getPlayer().getKiller().getUniqueId() + ".kills", String.valueOf(Integer.parseInt(Jedis.get(e.getPlayer().getKiller().getUniqueId() + ".kills")) + 1));
            }
            e.getPlayer().spigot().respawn();
            e.getPlayer().teleport(LocationUtils.spawnPoint("hub"));
            boolean h = false;
            boolean c = false;
            boolean l = false;
            boolean b = false;
            for (ItemStack it : stacks) {
                if (it.getType().name().endsWith("_HELMET") && !h) {
                    e.getPlayer().getInventory().setHelmet(it);
                    h = true;
                } else if (it.getType().name().endsWith("_CHESTPLATE") && !c) {
                    e.getPlayer().getInventory().setChestplate(it);
                    c = true;
                } else if (it.getType().name().endsWith("_LEGGINGS") && !l) {
                    e.getPlayer().getInventory().setLeggings(it);
                    l = true;
                } else if (it.getType().name().endsWith("_BOOTS") && !b) {
                    e.getPlayer().getInventory().setBoots(it);
                    b = true;
                } else {
                    e.getPlayer().getInventory().addItem(it);
                }
            }
        }, 2L);
    }
}

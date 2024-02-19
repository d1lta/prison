package me.d1lta.prison;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PrisonEvents implements Listener {

    static public void effectEveryone() {
        Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            switch (new Random().nextInt(1,4)) {
                case 1 -> Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> {
                    LittlePlayer pl = new LittlePlayer(it);
                    pl.sendTitle("Внезапное событие!", "Сила", 20, 20, 20);
                    pl.addEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
                });
                case 2 -> Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> {
                    LittlePlayer pl = new LittlePlayer(it);
                    pl.sendTitle("Внезапное событие!", "Скорость", 20, 20, 20);
                    pl.addEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
                });
                case 3 -> Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> {
                    LittlePlayer pl = new LittlePlayer(it);
                    pl.sendTitle("Внезапное событие!", "Регенерация", 20, 20, 20);
                    pl.addEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
                });
            }
        }, 300 * 20L, 300 * 20L);
    }

    public void applyEvents(LittlePlayer pl) {
        toiletEvents(pl);
        showerEvents(pl);
        sleepEvents(pl);
    }

    public void toiletEvents(LittlePlayer pl) {
        AtomicInteger chance = new AtomicInteger(pl.getToiletChance());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList().contains(pl.uuid)) {
                    pl.setToiletChance(chance.get());
                    this.cancel();
                }
                if (!pl.getToiletStatus()) {
                    if (new Random().nextInt(1, chance.get() + 1) == 1) {
                        pl.setToiletStatus(true);
                        pl.sendMessage(DComponent.create("Что-то я давно не срал.....", TextColor.color(249, 254, 255)));
                        pl.addEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, 1));
                        chance.set(pl.getToiletMaxChance());
                    }
                    chance.set(chance.get() - 1);
                }
            }
        }.runTaskTimer(Main.plugin, 20L, 20L);
    }

    public void showerEvents(LittlePlayer pl) {
        AtomicInteger chance = new AtomicInteger(pl.getShowerChance());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList().contains(pl.uuid)) {
                    pl.setShowerChance(chance.get());
                    this.cancel();
                }
                if (!pl.getShowerStatus()) {
                    if (new Random().nextInt(1, chance.get() + 1) == 1) {
                        pl.setShowerStatus(true);
                        pl.sendMessage(DComponent.create("Около меня уже мухи летают. Надо бы помыться...", TextColor.color(249, 254, 255)));
                        pl.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000000, 1));
                        chance.set(pl.getShowerMaxChance());
                    }
                    chance.set(chance.get() - 1);
                } else {
                    if (List.of(Material.WATER, Material.WATER_CAULDRON).contains(pl.getLocation().getBlock().getType())) {
                        pl.removeEffect(PotionEffectType.CONFUSION);
                        pl.setShowerStatus(false);
                        pl.sendMessage(
                                DComponent.create("Я подмылся!", TextColor.color(249, 254, 255)));
                    }
                }
            }
        }.runTaskTimer(Main.plugin, 20L, 20L);
    }

    public void sleepEvents(LittlePlayer pl) {
        AtomicInteger chance = new AtomicInteger(pl.getSleepChance());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList().contains(pl.uuid)) {
                    pl.setSleepChance(chance.get());
                    this.cancel();
                }
                if (!pl.getSleepStatus()) {
                    if (new Random().nextInt(1, chance.get() + 1) == 1) {
                        pl.setSleepStatus(true);
                        pl.sendMessage(
                                DComponent.create("Давненько я не спал... Надо бы поспать.", TextColor.color(249, 254, 255)));
                        pl.addEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000000000, 3));
                        chance.set(pl.getSleepMaxChance());
                    }
                    chance.set(chance.get() - 1);
                }
            }
        }.runTaskTimer(Main.plugin, 20L, 20L);
    }

    @EventHandler
    public void onToiletPaper(PlayerInteractEvent e) {
        if (!CheckUtils.checkForNull(e.getItem())) { return; }
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (NBT.getKeys(e.getItem()).contains("toiletpaper") && pl.getToiletStatus()) {
            pl.removeEffect(PotionEffectType.SLOW);
            pl.sendMessage(DComponent.create("Наконец-то я посрал!", TextColor.color(249, 254, 255)));
            pl.setToiletStatus(false);
            ItemStack stack = e.getItem();
            e.getPlayer().getInventory().getItemInMainHand().setAmount(stack.getAmount() - 1);
        }
    }

    @EventHandler
    public void onSleep(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) { return; }
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (e.getClickedBlock().getType().equals(Material.WHITE_BED) && pl.getSleepStatus()) {
            pl.setSleepStatus(false);
            pl.removeEffect(PotionEffectType.SLOW_DIGGING);
            pl.sendMessage(DComponent.create("Мне приснилось как я копал. Какой ужас!", TextColor.color(249, 254, 255)));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            checkDebuffs(new LittlePlayer(e.getPlayer().getUniqueId()));
        }, 5L);
    }

    public void checkDebuffs(LittlePlayer pl) {
        if (pl.getToiletStatus()) {
            pl.addEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, 1));
        }
        if (pl.getShowerStatus()) {
            pl.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000000000, 1));
        }
        if (pl.getSleepStatus()) {
            pl.addEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000000000, 3));
        }
    }
}

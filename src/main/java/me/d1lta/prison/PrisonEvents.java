package me.d1lta.prison;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.LittlePlayer;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PrisonEvents {

    static public void effectEveryone() {
        Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            switch (new Random().nextInt(1,4)) {
                case 1 -> Bukkit.getOnlinePlayers().stream().parallel().map(Entity::getUniqueId).forEach(it ->
                        addEffect(new LittlePlayer(it), "Внезапное событие!", "Сила", PotionEffectType.INCREASE_DAMAGE));
                case 2 -> Bukkit.getOnlinePlayers().stream().parallel().map(Entity::getUniqueId).forEach(it ->
                        addEffect(new LittlePlayer(it), "Внезапное событие!", "Скорость", PotionEffectType.SPEED));
                case 3 -> Bukkit.getOnlinePlayers().stream().parallel().map(Entity::getUniqueId).forEach(it ->
                        addEffect(new LittlePlayer(it), "Внезапное событие!", "Регенерация", PotionEffectType.REGENERATION));
            }
        }, 300 * 20L, 300 * 20L);
    }

    private static void addEffect(LittlePlayer pl, String upperTitle, String lowerTitle, PotionEffectType effectType) {
        pl.sendTitle(upperTitle, lowerTitle, 20, 20, 20);
        pl.addEffect(new PotionEffect(effectType, 100, 1));
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
                        pl.sendMessage(DComponent.create("Я подмылся!", TextColor.color(249, 254, 255)));
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
                        pl.sendMessage(DComponent.create("Давненько я не спал... Надо бы поспать.", TextColor.color(249, 254, 255)));
                        pl.addEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000000000, 3));
                        chance.set(pl.getSleepMaxChance());
                    }
                    chance.set(chance.get() - 1);
                }
            }
        }.runTaskTimer(Main.plugin, 20L, 20L);
    }


    public static void checkDebuffs(LittlePlayer pl) {
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

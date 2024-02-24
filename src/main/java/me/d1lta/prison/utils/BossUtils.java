package me.d1lta.prison.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import me.d1lta.prison.entities.bosses.Vindicator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class BossUtils {

    public static void giveMoney(Component bossName, double multiplier, Map<UUID, Double> damageDealers) {
        List<UUID> keys = Vindicator.damageDealers.entrySet().stream().parallel().sorted(Entry.<UUID, Double>comparingByValue().reversed()).limit(3).map(Entry::getKey).toList();
        Bukkit.getOnlinePlayers().stream().parallel().map(Entity::getUniqueId).forEach(uuid -> {
            LittlePlayer pl = new LittlePlayer(uuid);
            pl.sendMessage(bossName.append(DComponent.create(" повержен!")));
            pl.sendMessage("");
            keys.forEach(it -> pl.sendMessage(DComponent.create(String.valueOf(keys.indexOf(it) + 1)).append(
                    DComponent.create(". ")).append(
                    DComponent.create(new LittlePlayer(it).getName())).append(
                    DComponent.create(" - ")).append(
                    DComponent.create(String.valueOf((int) Double.parseDouble(damageDealers.get(it).toString())))).append(
                    DComponent.create(" урона"))));
            if (damageDealers.get(pl.uuid) != null) {
                pl.sendMessage("");
                pl.sendMessage("Вы заработали " + (int) (damageDealers.get(pl.uuid) * multiplier) + "$");
                pl.addMoney(damageDealers.get(pl.uuid) * multiplier, "boss");
            }
        });
    }
}

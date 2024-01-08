package me.d1lta.prison;

import me.d1lta.prison.utils.ComponentUtils;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NumberUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.w3c.dom.Text;

public class ScoreboardP {

    public void scoreboard(LittlePlayer player) {
        ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Test", "Dummy", ComponentUtils.component("Prison Classic"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team blocks = scoreboard.registerNewTeam("blocks");
        blocks.addEntry("Вскопано блоков » §b");
        blocks.suffix(ComponentUtils.component(NumberUtils.withK(Integer.parseInt(Jedis.get(player.uuid + ".blocks"))), TextColor.color(255, 170, 0)));

        Team money = scoreboard.registerNewTeam("money");
        money.addEntry("Баланс » §b");
        money.suffix(ComponentUtils.component(String.format("%.2f", Double.valueOf(Jedis.get(player.uuid + ".money"))) + "$", TextColor.color(85,255,85)));

        Team level = scoreboard.registerNewTeam("level");
        level.addEntry("Уровень » §b");
        level.suffix(ComponentUtils.component(Jedis.get(player.uuid + ".lvl"), TextColor.color(255, 255, 85)));

        Team rats = scoreboard.registerNewTeam("rats");
        rats.addEntry("Крыс убито » §b");
        rats.suffix(ComponentUtils.component(Jedis.get(player.uuid + ".rats")));

        Team kills = scoreboard.registerNewTeam("kills");
        kills.addEntry("Убийств » §b");
        kills.suffix(ComponentUtils.component(Jedis.get(player.uuid + ".kills")));

        Team deaths = scoreboard.registerNewTeam("deaths");
        deaths.addEntry("Смертей » §b");
        deaths.suffix(ComponentUtils.component(Jedis.get(player.uuid + ".deaths")));

        Team online = scoreboard.registerNewTeam("online");
        online.addEntry("Онлайн » §b");
        online.suffix(ComponentUtils.component(String.valueOf(Bukkit.getServer().getOnlinePlayers().size())));

        Team faction = scoreboard.registerNewTeam("faction");
        faction.addEntry("Фракция » §b");
        TextColor color = switch (Jedis.get(player.uuid + ".faction")) {
            case "Ниггеры" -> TextColor.color(75, 77, 79);
            case "Латиносы" -> TextColor.color(153, 90, 24);
            case "Белые" -> TextColor.color(224, 223, 219);
            case "Азиаты" -> TextColor.color(255, 229, 0);
            default -> TextColor.color(255, 255, 255);
        };
        faction.suffix(ComponentUtils.component(Jedis.get(player.uuid + ".faction"), color));

        objective.getScore("Игрок").setScore(13);
        objective.getScore("Уровень » §b").setScore(12);
        objective.getScore("Баланс » §b").setScore(11);
        objective.getScore("Фракция » §b").setScore(10);
        objective.getScore("  ").setScore(9);
        objective.getScore("Статистика").setScore(8);
        objective.getScore("Убийств » §b").setScore(7);
        objective.getScore("Смертей » §b").setScore(6);
        objective.getScore("Вскопано блоков » §b").setScore(5);
        objective.getScore("Крыс убито » §b").setScore(4);
        objective.getScore("   ").setScore(3);
        objective.getScore("Сервер").setScore(2);
        objective.getScore("Онлайн » §b").setScore(1);
        player.setScoreboard(scoreboard);
    }

}

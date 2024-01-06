package me.d1lta.prison;

import me.d1lta.prison.utils.NumberUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardP {

    public void scoreboard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Test", "Dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.setDisplayName("dasha sosalka");

        Team blocks = scoreboard.registerNewTeam("blocks");
        blocks.addEntry("Кол-во блоков: §b");
        blocks.suffix(Component.text(NumberUtils.withK(Integer.parseInt(Jedis.get(player.getUniqueId() + ".blocks")))));

        Team money = scoreboard.registerNewTeam("money");
        money.addEntry("Баланс: §b");
        money.suffix(Component.text(String.format("%.2f", Double.valueOf(Jedis.get(player.getUniqueId() + ".money"))) + "$"));

        Team level = scoreboard.registerNewTeam("level");
        level.addEntry("Уровень: §b");
        level.suffix(Component.text(Jedis.get(player.getUniqueId() + ".lvl")));


        objective.getScore("Игрок").setScore(8);
        objective.getScore("Уровень: §b").setScore(8);
        objective.getScore("Фракция: говноед").setScore(7);
        objective.getScore("Баланс: §b").setScore(6);
        objective.getScore("").setScore(5);
        objective.getScore("Статистика").setScore(4);
        objective.getScore("Убийств: §b").setScore(3);
        objective.getScore("Смертей: §b").setScore(2);
        objective.getScore("Кол-во блоков: §b").setScore(1);
        objective.getScore("Убито крыс: §b").setScore(0);

        player.setScoreboard(scoreboard);
    }

}

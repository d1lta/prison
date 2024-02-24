package me.d1lta.prison.commands;

import me.d1lta.prison.entities.traders.ElderVillager;
import me.d1lta.prison.entities.bosses.Vindicator;
import me.d1lta.prison.entities.mobs.PrisonBat;
import me.d1lta.prison.entities.mobs.PrisonRat;
import me.d1lta.prison.entities.mobs.PrisonZombie;
import me.d1lta.prison.entities.traders.StartVillager;
import me.d1lta.prison.entities.traders.TrainerVillager;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SummonMob implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            LittlePlayer pl = new LittlePlayer(((Player) sender).getUniqueId());
            switch (args[0]) {
                case "vindicator" -> new Vindicator(pl.getLocation());

                case "startvillager" -> new StartVillager(pl.getLocation());
                case "trainer" -> new TrainerVillager(pl.getLocation());
                case "eldervillager" -> new ElderVillager(pl.getLocation());

                case "rat" -> new PrisonRat(pl.getLocation());
                case "zombie" -> new PrisonZombie(pl.getLocation());
                case "bat" -> new PrisonBat(pl.getLocation());
            }
            return true;
        }
        return false;
    }
}

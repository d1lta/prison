package me.d1lta.prison.commands;

import me.d1lta.prison.mobs.PBat;
import me.d1lta.prison.mobs.PZombie;
import me.d1lta.prison.mobs.Rat;
import me.d1lta.prison.mobs.bosses.Vindicator;
import me.d1lta.prison.mobs.traders.ElderVillager;
import me.d1lta.prison.mobs.traders.StartVillager;
import me.d1lta.prison.mobs.traders.Trainer;
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
                case "rat" -> new Rat(pl.getLocation());
                case "bat" -> new PBat(pl.getLocation());
                case "zombie" -> new PZombie(pl.getLocation());
                case "vindicator" -> new Vindicator(pl.getLocation());
                case "startvillager" -> new StartVillager(pl.getLocation());
                case "eldervillager" -> new ElderVillager(pl.getLocation());
                case "trainer" -> new Trainer(pl.getLocation());
            }
        }
        return false;
    }
}

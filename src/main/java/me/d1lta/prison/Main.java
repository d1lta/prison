package me.d1lta.prison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.d1lta.prison.commands.AdmJedis;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.commands.Blockstats;
import me.d1lta.prison.commands.Debug;
import me.d1lta.prison.commands.GiveItem;
import me.d1lta.prison.commands.Level;
import me.d1lta.prison.commands.Mine;
import me.d1lta.prison.commands.SellCmd;
import me.d1lta.prison.commands.Spawn;
import me.d1lta.prison.commands.SummonMob;
import me.d1lta.prison.commands.Upgrades;
import me.d1lta.prison.commands.WorldCreate;
import me.d1lta.prison.commands.WorldTp;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.events.DisableBlockPhysics;
import me.d1lta.prison.events.BlockBreak;
import me.d1lta.prison.events.BlockPlace;
import me.d1lta.prison.events.EntityDeath;
import me.d1lta.prison.events.PlayerDeath;
import me.d1lta.prison.events.onInteract;
import me.d1lta.prison.events.onJoin;
import me.d1lta.prison.events.onSpawnEntity;
import me.d1lta.prison.mines.MinesTimer;
import me.d1lta.prison.mobs.bosses.Vindicator;
import me.d1lta.prison.utils.LittlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public final class Main extends JavaPlugin {

    public static Plugin plugin;
    public static Config config;
    public static boolean isLoaded = false;
    public static JedisPool pool;

    @Override
    public void onEnable() {
        pool = new JedisPool("localhost", 6379);
        try {
            pool.getResource();
        } catch (Exception ex) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        plugin = this;
        config = new Config(this);
        Bukkit.setSpawnRadius(0);

        registerCommands();
        registerEvents();
        worldLoader();
        timer();
    }

    @Override
    public void onDisable() {
        pool.close();
    }

    public void timer() {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            new MinesTimer().timer();
        }, 100L);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(it -> new ScoreboardP().scoreboard(new LittlePlayer(it.getUniqueId())));

        }, 20L, 20L);
    }

    private static void worldLoader() {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (config.getConfig().getConfigurationSection("worlds") != null) {
                config.getConfig().getConfigurationSection("worlds").getKeys(false).forEach(it -> {
                        new WorldCreator(it).createWorld();
                });
            }
            isLoaded = true;
        },100L);
    }

    private void registerEvents() {
        List<Listener> events = List.of(
                new onJoin(),
                new BlockBreak(),
                new DisableBlockPhysics(),
                new BlockPlace(),
                new onInteract(),
                new onSpawnEntity(),
                new Level(),
                new PlayerDeath(),
                new Mine(),
                new Blockstats(),
                new Upgrade(),
                new Upgrades(),
                new EntityDeath(),
                new Vindicator(null));
        Bukkit.getPluginManager().registerEvents(config, plugin);
        events.forEach(it -> Bukkit.getPluginManager().registerEvents(it, plugin));
    }

    private void registerCommands() {
        Map<String, CommandExecutor> commands = new HashMap<>(Map.of(
                "config", new me.d1lta.prison.commands.Config(),
                "wcreate", new WorldCreate(),
                "wtp", new WorldTp(),
                "giveitem", new GiveItem(),
                "sell", new SellCmd(),
                "autosell", new AutoSell(),
                "upgrade", new Upgrade(),
                "admjedis", new AdmJedis(),
                "lvl", new Level(),
                "spawn", new Spawn()));
        commands.putAll(Map.of(
                "mine", new Mine(),
                "debug", new Debug(),
                "blockstats", new Blockstats(),
                "upgrades", new Upgrades(),
                "summonmob", new SummonMob()));
        commands.forEach((cmd, executor) -> getServer().getPluginCommand(cmd).setExecutor(executor));
    }
}

package me.d1lta.prison;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.d1lta.prison.chests.DefaultChest;
import me.d1lta.prison.chests.EnderChest;
import me.d1lta.prison.commands.AdmJedis;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.commands.Base;
import me.d1lta.prison.commands.Blockstats;
import me.d1lta.prison.commands.Debug;
import me.d1lta.prison.commands.Faction;
import me.d1lta.prison.commands.Gift;
import me.d1lta.prison.commands.GiveItem;
import me.d1lta.prison.commands.Level;
import me.d1lta.prison.commands.Mine;
import me.d1lta.prison.commands.SellCmd;
import me.d1lta.prison.commands.Spawn;
import me.d1lta.prison.commands.SummonMob;
import me.d1lta.prison.commands.Upgrades;
import me.d1lta.prison.commands.Warzone;
import me.d1lta.prison.commands.WorldCreate;
import me.d1lta.prison.commands.WorldTp;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.events.DisableBlockPhysics;
import me.d1lta.prison.events.BlockBreak;
import me.d1lta.prison.events.BlockPlace;
import me.d1lta.prison.events.EntityDeath;
import me.d1lta.prison.events.ItemDrop;
import me.d1lta.prison.events.PlayerDeath;
import me.d1lta.prison.events.PlayerFaction;
import me.d1lta.prison.events.onInteract;
import me.d1lta.prison.events.onJoin;
import me.d1lta.prison.events.onSpawnEntity;
import me.d1lta.prison.items.VaultAccess;
import me.d1lta.prison.mines.MinesTimer;
import me.d1lta.prison.mobs.bosses.Vindicator;
import me.d1lta.prison.mobs.traders.ElderVillager;
import me.d1lta.prison.mobs.traders.StartVillager;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.warzone.Point;
import me.d1lta.prison.warzone.WarzoneCapture;
import me.d1lta.prison.worldGenerators.VoidGen;
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
        Main.config.getConfig().getConfigurationSection("warzonepoints").getKeys(false).forEach(it -> {
            WarzoneCapture.getPoint(WarzoneCapture.getCentralPoint(it)).refreshColor(WarzoneCapture.getCentralPoint(it));
            WarzoneCapture.getCentralPoint(it).getBlock().setType(Factions.NO_FACTION.getWarzoneGlassMat());
        });
        Bukkit.getLogger().warning("DISABLED PRISON");
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
                    new WorldCreator(it).generator(new VoidGen()).createWorld();
                });
                DefaultChest.initLoc();
                DefaultChest.spawnChest();
                EnderChest.initLoc();
                EnderChest.spawnChest();
            }
        },40L);
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
                new DefaultChest(),
                new EnderChest(),
                new VaultAccess(),
                new Faction(),
                new PlayerFaction(),
                new ItemDrop(),
                new WarzoneCapture(),
                new Vindicator(null),
                new StartVillager(null),
                new ElderVillager(null));
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
                "summonmob", new SummonMob(),
                "faction", new Faction(),
                "base", new Base(),
                "gift", new Gift(),
                "warzone", new Warzone()));
        commands.forEach((cmd, executor) -> getServer().getPluginCommand(cmd).setExecutor(executor));
    }
}

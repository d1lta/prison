package me.d1lta.prison;

import static me.d1lta.prison.PrisonEvents.effectEveryone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.boosters.BlockBoostHandler;
import me.d1lta.prison.chests.DefaultChest;
import me.d1lta.prison.chests.EnderChest;
import me.d1lta.prison.commands.AdmJedis;
import me.d1lta.prison.commands.AutoSell;
import me.d1lta.prison.commands.Base;
import me.d1lta.prison.commands.Blockstats;
import me.d1lta.prison.commands.Debug;
import me.d1lta.prison.commands.EnchantmentList;
import me.d1lta.prison.commands.Faction;
import me.d1lta.prison.commands.Gift;
import me.d1lta.prison.commands.GiveItem;
import me.d1lta.prison.commands.Level;
import me.d1lta.prison.commands.Mine;
import me.d1lta.prison.commands.SellCmd;
import me.d1lta.prison.commands.Spawn;
import me.d1lta.prison.commands.SummonMob;
import me.d1lta.prison.commands.Upgrade;
import me.d1lta.prison.commands.Upgrades;
import me.d1lta.prison.commands.Warzone;
import me.d1lta.prison.commands.WorldCreate;
import me.d1lta.prison.commands.WorldTp;
import me.d1lta.prison.enchants.enchantments.armor.CatGrace;
import me.d1lta.prison.enchants.enchantments.armor.Fiery;
import me.d1lta.prison.enchants.enchantments.armor.Ninja;
import me.d1lta.prison.enchants.enchantments.armor.Sacred;
import me.d1lta.prison.enchants.enchantments.armor.Strenghtening;
import me.d1lta.prison.enchants.enchantments.instruments.Archaeology;
import me.d1lta.prison.enchants.enchantments.instruments.Hammer;
import me.d1lta.prison.enchants.enchantments.sword.Blindness;
import me.d1lta.prison.enchants.enchantments.sword.Boxer;
import me.d1lta.prison.enchants.enchantments.sword.Confusion;
import me.d1lta.prison.enchants.enchantments.sword.Fury;
import me.d1lta.prison.enchants.enchantments.sword.Toxic;
import me.d1lta.prison.enchants.enchantments.sword.Vampirism;
import me.d1lta.prison.enchants.enchantments.sword.Vortex;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.events.BlockBreak;
import me.d1lta.prison.events.BlockPlace;
import me.d1lta.prison.events.EntityDamage;
import me.d1lta.prison.events.EntityDamageByEntity;
import me.d1lta.prison.events.EntityDeath;
import me.d1lta.prison.events.EntitySpawn;
import me.d1lta.prison.events.InventoryClick;
import me.d1lta.prison.events.PhysicsEvents;
import me.d1lta.prison.events.PlayerDeath;
import me.d1lta.prison.events.PlayerDropItem;
import me.d1lta.prison.events.PlayerInteract;
import me.d1lta.prison.events.PlayerInteractAtEntity;
import me.d1lta.prison.events.PlayerJoin;
import me.d1lta.prison.events.PlayerMove;
import me.d1lta.prison.events.PlayerQuit;
import me.d1lta.prison.mines.MinesTimer;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.warzone.WarzoneCapture;
import me.d1lta.prison.worldGenerators.VoidGen;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public final class Main extends JavaPlugin {

    public static Plugin plugin;
    public static Config config;
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
        effectEveryone();
    }

    @Override
    public void onDisable() {
        Main.config.getConfig().getConfigurationSection("warzonepoints").getKeys(false).forEach(it -> {
            WarzoneCapture.getPoint(WarzoneCapture.getCentralPoint(it)).refreshColor(WarzoneCapture.getCentralPoint(it));
            WarzoneCapture.getCentralPoint(it).getBlock().setType(Factions.NO_FACTION.getWarzoneGlassMat());
        });
        BlockBoostHandler.applyToDBEveryone();
        PlayerValues.save();
        Bukkit.getLogger().warning("DISABLED PRISON");
        pool.close();
    }

    AtomicInteger count = new AtomicInteger(0);

    public void timer() {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            new MinesTimer().timer();
        }, 100L);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (count.get() % 10 == 0) {
                BlockBoostHandler.applyToDBEveryone();
            }
            Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> new PrisonEvents().checkDebuffs(new LittlePlayer(it)));
            Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> new ScoreboardP().scoreboard(new LittlePlayer(it)));
            count.set(count.get() + 1);

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
        }, 40L);
    }

    private void registerEvents() {
        List<Listener> events = List.of(
                new BlockBreak(),
                new BlockPlace(),
                new EntityDamage(),
                new EntityDamageByEntity(),
                new EntityDeath(),
                new EntitySpawn(),
                new InventoryClick(),
                new PhysicsEvents(),
                new PlayerDeath(),
                new PlayerDropItem(),
                new PlayerInteract(),
                new PlayerInteractAtEntity(),
                new PlayerJoin(),
                new PlayerMove(),
                new PlayerQuit(),

                new Vampirism(), // TODO: убрать это всё в один метод
                new CatGrace(),
                new Archaeology(),
                new Hammer(),
                new Sacred(),
                new Fiery(),
                new Blindness(),
                new Boxer(),
                new Confusion(),
                new Fury(),
                new Ninja(),
                new Strenghtening(),
                new Toxic(),
                new Vortex());
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
                "warzone", new Warzone(),
                "enchantments", new EnchantmentList()
        ));
        commands.forEach((cmd, executor) -> getServer().getPluginCommand(cmd).setExecutor(executor));
    }
}

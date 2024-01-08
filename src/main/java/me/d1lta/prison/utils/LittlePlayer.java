package me.d1lta.prison.utils;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

public class LittlePlayer {

    public UUID uuid;

    public LittlePlayer(UUID uuid) {
        if (Bukkit.getPlayer(uuid) != null) {
            this.uuid = uuid;
        }
    }

    public String getName() { return Bukkit.getPlayer(uuid).getName(); }

    public Location getLocation() {
        return Bukkit.getPlayer(uuid).getLocation();
    }

    public GameMode getGameMode() { return Bukkit.getPlayer(uuid).getGameMode(); }

    public void teleport(Location loc) { Bukkit.getPlayer(uuid).teleport(loc); }

    public World getWorld() { return Bukkit.getPlayer(uuid).getWorld(); }
    public double getX() { return Bukkit.getPlayer(uuid).getX(); }

    public double getY() { return Bukkit.getPlayer(uuid).getY(); }

    public double getZ() { return Bukkit.getPlayer(uuid).getZ(); }

    public void sendMessage(String text) { Bukkit.getPlayer(uuid).sendMessage(text); }

    public void sendMessage(Component text) { Bukkit.getPlayer(uuid).sendMessage(text); }

    public void sendTitle(String up, String down, int a, int b, int c) { Bukkit.getPlayer(uuid).sendTitle(up, down, a, b, c); }

    public void sendLittleTitle(TextComponent text) { Bukkit.getPlayer(uuid).spigot().sendMessage(ChatMessageType.ACTION_BAR, text); }

    public void setScoreboard(Scoreboard board) { Bukkit.getPlayer(uuid).setScoreboard(board); }

    public Inventory getInventory() { return Bukkit.getPlayer(uuid).getInventory(); }

    public boolean isOp() { return Bukkit.getPlayer(uuid).isOp(); }

    public void openInventory(Inventory inv) { Bukkit.getPlayer(uuid).openInventory(inv); }

    public void closeInventory() { Bukkit.getPlayer(uuid).closeInventory(); }

    public ItemStack getItemInMainHand() { return Bukkit.getPlayer(uuid).getInventory().getItemInMainHand(); }

    public int getHeldItemSlot() { return Bukkit.getPlayer(uuid).getInventory().getHeldItemSlot(); }
}

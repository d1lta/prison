package me.d1lta.prison.utils;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.boosters.BlockBoostHandler;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.enums.TrainerSkills;
import me.d1lta.prison.warzone.MoneyPoint;
import me.d1lta.prison.warzone.WarzoneCapture;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class LittlePlayer {

    public UUID uuid;

    public LittlePlayer(UUID uuid) {
        if (Bukkit.getPlayer(uuid) != null) {
            this.uuid = uuid;
        }
    }

    public void dropItem() {
        this.getWorld().dropItem(this.getLocation(), this.getItemInMainHand());
        this.getItemInMainHand().setAmount(0);
    }

    public void giveEffect(PotionEffect effect) { Bukkit.getPlayer(uuid).addPotionEffect(effect); }

    public void giveItem(ItemStack stack) {
        Bukkit.getPlayer(uuid).getInventory().addItem(stack);
    }

    public void addKill() { Jedis.set(uuid + ".kills", String.valueOf(this.getKills() + 1));  }

    public int getKills() { return Integer.parseInt(Jedis.get(uuid + ".kills"));  }

    public void addDeath() { Jedis.set(uuid + ".deaths", String.valueOf(this.getDeaths() + 1));  }

    public int getDeaths() { return Integer.parseInt(Jedis.get(uuid + ".deaths"));  }

    public int getBlocks(String blockType) { return Integer.parseInt(Jedis.get(uuid + ".blocks." + blockType.toLowerCase())); }

    public int getBlocks() { return Integer.parseInt(Jedis.get(uuid + ".blocks")); }

    public boolean hasVaultAccess() { return Jedis.get(uuid + ".vault").equals("true"); }

    public double getMoney() {
        return Double.parseDouble(Jedis.get(uuid + ".money"));
    }

    public int getIntMoney() {
        return (int) Double.parseDouble(Jedis.get(uuid + ".money"));
    }

    public int getRats() { return Integer.parseInt(Jedis.get(uuid + ".rats")); }

    public void addRat() { Jedis.set(uuid + ".rats", String.valueOf(this.getRats() + 1)); }

    public int getZombies() { return Integer.parseInt(Jedis.get(uuid + ".zombie")); }

    public void addZombie() { Jedis.set(uuid + ".zombie", String.valueOf(this.getZombies() + 1)); }

    public int getBats() { return Integer.parseInt(Jedis.get(uuid + ".bat")); }

    public void addBat() { Jedis.set(uuid + ".bat", String.valueOf(this.getBats() + 1)); }

    public void addBlock(Material mat) { Jedis.set(uuid + ".blocks." + mat.name().toLowerCase(), String.valueOf(this.getBlocks(mat.name()) + 1)); }

    public void addBlock() { Jedis.set(uuid + ".blocks", String.valueOf(this.getBlocks() + 1)); }

    public void addBlocks(int amount) { Jedis.set(uuid + ".blocks", String.valueOf(this.getBlocks() + amount)); }

    public void addBoostedBlocks() { BlockBoostHandler.applyToDB(this); }

    public double addMoney(double amount, String type) {
        if (this.getFaction().equals(WarzoneCapture.money.capturedBy) && type.equals("sell")) {
            amount = amount * MoneyPoint.modifier;
        }
        Jedis.set(uuid + ".money", String.valueOf(Double.parseDouble(Jedis.get(uuid + ".money")) + amount));
        return amount;
    }

    public void addMoney(int amount) { Jedis.set(uuid + ".money", String.valueOf(Double.parseDouble(Jedis.get(uuid + ".money")) + amount)); }

    public void removeMoney(double amount) { Jedis.set(uuid + ".money", String.valueOf(Double.parseDouble(Jedis.get(uuid + ".money")) - amount)); }

    public void removeMoney(int amount) { Jedis.set(uuid + ".money", String.valueOf(Double.parseDouble(Jedis.get(uuid + ".money")) - amount)); }

    public int getLevel() { return Integer.parseInt(Jedis.get(uuid + ".lvl")); }

    public void lvlUp() { Jedis.set(uuid + ".lvl", String.valueOf(this.getLevel() + 1)); }

    public Factions getFaction() { return Factions.getFaction(Jedis.get(uuid + ".faction")); }

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

    public @NotNull Collection<PotionEffect> getEffects() { return Bukkit.getPlayer(this.uuid).getActivePotionEffects(); }

    public void removeEffect(PotionEffectType effectType) { Bukkit.getPlayer(this.uuid).removePotionEffect(effectType); }

    public void addEffect(PotionEffect effect) { Bukkit.getPlayer(this.uuid).addPotionEffect(effect); }

    public void playSound(Sound sound, float volume, float pitch) { Bukkit.getPlayer(this.uuid).playSound(Bukkit.getPlayer(this.uuid).getLocation(), sound, volume, pitch); }

    public ItemStack getHelmet() { return Bukkit.getPlayer(this.uuid).getInventory().getHelmet(); }

    public ItemStack getChestplate() { return Bukkit.getPlayer(this.uuid).getInventory().getChestplate(); }

    public ItemStack getLeggings() { return Bukkit.getPlayer(this.uuid).getInventory().getLeggings(); }

    public ItemStack getBoots() { return Bukkit.getPlayer(this.uuid).getInventory().getBoots(); }

    public List<ItemStack> getArmor() { return List.of(this.getHelmet(), this.getChestplate(), this.getLeggings(), this.getBoots()); }

    public void damage(double value) { Bukkit.getPlayer(this.uuid).damage(value); }

    public void damage(double value, Entity entity) { Bukkit.getPlayer(this.uuid).damage(value, entity); }

    public void setMaxHP(double value) { Bukkit.getPlayer(this.uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value); }

    public void addVelocity(Vector value) { Bukkit.getPlayer(this.uuid).setVelocity(value); }

    public Entity castToEntity() { return Bukkit.getPlayer(this.uuid); }

    public void burn(int ticks) { Bukkit.getPlayer(this.uuid).setFireTicks(ticks); }

    public boolean getToiletStatus() { return Boolean.parseBoolean(Jedis.get(this.uuid + ".toilet")); }

    public void setToiletStatus(boolean status) { Jedis.set(this.uuid + ".toilet", String.valueOf(status)); }

    public boolean getShowerStatus() { return Boolean.parseBoolean(Jedis.get(this.uuid + ".shower")); }

    public void setShowerStatus(boolean status) { Jedis.set(this.uuid + ".shower", String.valueOf(status)); }

    public boolean getSleepStatus() { return Boolean.parseBoolean(Jedis.get(this.uuid + ".sleep")); }

    public void setSleepStatus(boolean status) { Jedis.set(this.uuid + ".sleep", String.valueOf(status)); }

    public int getToiletChance() { return Integer.parseInt(Jedis.get(this.uuid + ".toilet_chance")); }

    public void setToiletChance(int chance) {  Jedis.set(this.uuid + ".toilet_chance", String.valueOf(chance)); }

    public int getToiletMaxChance() { return Integer.parseInt(Jedis.get(this.uuid + ".toilet_chance_max")); }

    public void setToiletMaxChance (int chance) {  Jedis.set(this.uuid + ".toilet_chance_max", String.valueOf(chance)); }

    public int getShowerChance() { return Integer.parseInt(Jedis.get(this.uuid + ".shower_chance")); }

    public void setShowerChance(int chance) {  Jedis.set(this.uuid + ".shower_chance", String.valueOf(chance)); }

    public int getShowerMaxChance() { return Integer.parseInt(Jedis.get(this.uuid + ".shower_chance_max")); }

    public void setShowerMaxChance (int chance) {  Jedis.set(this.uuid + ".shower_chance_max", String.valueOf(chance)); }

    public int getSleepChance() { return Integer.parseInt(Jedis.get(this.uuid + ".sleep_chance")); }

    public void setSleepChance(int chance) {  Jedis.set(this.uuid + ".sleep_chance", String.valueOf(chance)); }

    public int getSleepMaxChance() { return Integer.parseInt(Jedis.get(this.uuid + ".sleep_chance_max")); }

    public void setSleepMaxChance (int chance) {  Jedis.set(this.uuid + ".sleep_chance_max", String.valueOf(chance)); }

    public int getStrengthLvl() { return Integer.parseInt(Jedis.get(this.uuid + ".strength")); }

    public void setStrengthLvl(int lvl) { Jedis.set(this.uuid + ".strength", String.valueOf(lvl)); }

    public int getAgilityLvl() { return Integer.parseInt(Jedis.get(this.uuid + ".agility")); }

    public void setAgilityLvl(int lvl) { Jedis.set(this.uuid + ".agility", String.valueOf(lvl)); }

    public int getNeedsLvl() { return Integer.parseInt(Jedis.get(this.uuid + ".needs")); }

    public void setNeedsLvl(int lvl) { Jedis.set(this.uuid + ".needs", String.valueOf(lvl)); }

    public void updateTrainerSkills() {
        String chance = String.valueOf(TrainerSkills.getSkills(this.getNeedsLvl()).getNeeds_chance());
        Jedis.set(uuid + ".toilet_chance_max", chance);
        Jedis.set(uuid + ".shower_chance_max", chance);
        Jedis.set(uuid + ".sleep_chance_max", chance);
    }

    public void addHP(double amount) {
        if (Bukkit.getPlayer(this.uuid).getHealth() + amount > Bukkit.getPlayer(this.uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            Bukkit.getPlayer(this.uuid).setHealth(Bukkit.getPlayer(this.uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            return;
        }
        Bukkit.getPlayer(this.uuid).setHealth(Bukkit.getPlayer(this.uuid).getHealth() + amount);
    }

}

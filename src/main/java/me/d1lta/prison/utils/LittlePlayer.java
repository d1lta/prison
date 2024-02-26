package me.d1lta.prison.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import me.d1lta.prison.Jedis;
import me.d1lta.prison.PlayerValues;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.enums.TrainerSkills;
import me.d1lta.prison.utils.DComponent.CValues;
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
import org.bukkit.attribute.AttributeInstance;
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
        getWorld().dropItem(getLocation(), getItemInMainHand());
        getItemInMainHand().setAmount(0);
    }

    public void giveEffect(PotionEffect effect) { Bukkit.getPlayer(uuid).addPotionEffect(effect); }

    public void giveItem(ItemStack stack) {
        Bukkit.getPlayer(uuid).getInventory().addItem(stack);
    }

    public void addKill() { Jedis.set(uuid + ".kills", String.valueOf(getKills() + 1));  }

    public int getKills() { return Integer.parseInt(Jedis.get(uuid + ".kills"));  }

    public void addDeath() { Jedis.set(uuid + ".deaths", String.valueOf(getDeaths() + 1));  }

    public int getDeaths() { return Integer.parseInt(Jedis.get(uuid + ".deaths"));  }

    public boolean hasVaultAccess() { return Jedis.get(uuid + ".vault").equals("true"); }

    public void giveVaultAccess() { Jedis.set(uuid + ".vault", "true"); }

    public int getBlocks(String blockType) { return PlayerValues.getCertainBlock(uuid, Material.getMaterial(blockType)); }

    public int getBlocks(Material material) { return PlayerValues.getCertainBlock(uuid, material); }

    public void addBlock(Material mat) { PlayerValues.addCertainBlock(uuid, mat); }

    public void addBlock() { PlayerValues.addBlock(uuid); }

    public void addBlocks(int amount) { PlayerValues.addBlocks(uuid, amount); }

    public int getBlocks() { return PlayerValues.getBlocks(uuid); }

    public double getMoney() { return PlayerValues.getMoney(uuid); }

    public int getIntMoney() { return PlayerValues.getMoney(uuid).intValue(); }

    public int getRats() { return Integer.parseInt(Jedis.get(uuid + ".rats")); }

    public void addRat() { Jedis.set(uuid + ".rats", String.valueOf(getRats() + 1)); }

    public int getZombies() { return Integer.parseInt(Jedis.get(uuid + ".zombie")); }

    public void addZombie() { Jedis.set(uuid + ".zombie", String.valueOf(getZombies() + 1)); }

    public int getBats() { return Integer.parseInt(Jedis.get(uuid + ".bat")); }

    public void addBat() { Jedis.set(uuid + ".bat", String.valueOf(getBats() + 1)); }

    public double addMoney(double amount, String type) {
        if (getFaction().equals(WarzoneCapture.money.capturedBy) && type.equals("sell")) {
            amount = amount * MoneyPoint.modifier;
        }
        PlayerValues.addMoney(uuid, amount);
        return amount;
    }

    public void addMoney(int amount) { PlayerValues.addMoney(uuid, (double) amount); }

    public void removeMoney(double amount) { PlayerValues.removeMoney(uuid, amount); }

    public void removeMoney(int amount) { PlayerValues.removeMoney(uuid, amount); }

    public int getLevel() { return Integer.parseInt(Jedis.get(uuid + ".lvl")); }

    public void lvlUp() { Jedis.set(uuid + ".lvl", String.valueOf(getLevel() + 1)); }

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

    public void sendMessage(String text) { Bukkit.getPlayer(uuid).sendMessage(CValues.get(text).create()); }

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

    public @NotNull Collection<PotionEffect> getEffects() { return Bukkit.getPlayer(uuid).getActivePotionEffects(); }

    public void removeEffect(PotionEffectType effectType) { Bukkit.getPlayer(uuid).removePotionEffect(effectType); }

    public void addEffect(PotionEffect effect) { Bukkit.getPlayer(uuid).addPotionEffect(effect); }

    public void playSound(Sound sound, float volume, float pitch) { Bukkit.getPlayer(uuid).playSound(Bukkit.getPlayer(uuid).getLocation(), sound, volume, pitch); }

    public ItemStack getHelmet() { return Bukkit.getPlayer(uuid).getInventory().getHelmet(); }

    public ItemStack getChestplate() { return Bukkit.getPlayer(uuid).getInventory().getChestplate(); }

    public ItemStack getLeggings() { return Bukkit.getPlayer(uuid).getInventory().getLeggings(); }

    public ItemStack getBoots() { return Bukkit.getPlayer(uuid).getInventory().getBoots(); }

    public List<ItemStack> getArmor() {
        List<ItemStack> armor = new ArrayList<>();
        if (getHelmet() != null) { armor.add(getHelmet()); }
        if (getChestplate() != null) { armor.add(getChestplate()); }
        if (getLeggings() != null) { armor.add(getLeggings()); }
        if (getBoots() != null) { armor.add(getBoots()); }
        return armor;
    }

    public void damage(double value) { Bukkit.getPlayer(uuid).damage(value); }

    public void damage(double value, Entity entity) { Bukkit.getPlayer(uuid).damage(value, entity); }

    public void setMaxHP(double value) { Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value); }

    public void addVelocity(Vector value) { Bukkit.getPlayer(uuid).setVelocity(value); }

    public Entity castToEntity() { return Bukkit.getPlayer(uuid); }

    public void burn(int ticks) { Bukkit.getPlayer(uuid).setFireTicks(ticks); }

    public boolean getToiletStatus() { return Boolean.parseBoolean(Jedis.get(uuid + ".toilet")); }

    public void setToiletStatus(boolean status) { Jedis.set(uuid + ".toilet", String.valueOf(status)); }

    public boolean getShowerStatus() { return Boolean.parseBoolean(Jedis.get(uuid + ".shower")); }

    public void setShowerStatus(boolean status) { Jedis.set(uuid + ".shower", String.valueOf(status)); }

    public boolean getSleepStatus() { return Boolean.parseBoolean(Jedis.get(uuid + ".sleep")); }

    public void setSleepStatus(boolean status) { Jedis.set(uuid + ".sleep", String.valueOf(status)); }

    public int getToiletChance() { return Integer.parseInt(Jedis.get(uuid + ".toilet_chance")); }

    public void setToiletChance(int chance) {  Jedis.set(uuid + ".toilet_chance", String.valueOf(chance)); }

    public int getToiletMaxChance() { return Integer.parseInt(Jedis.get(uuid + ".toilet_chance_max")); }

    public void setToiletMaxChance (int chance) {  Jedis.set(uuid + ".toilet_chance_max", String.valueOf(chance)); }

    public int getShowerChance() { return Integer.parseInt(Jedis.get(uuid + ".shower_chance")); }

    public void setShowerChance(int chance) {  Jedis.set(uuid + ".shower_chance", String.valueOf(chance)); }

    public int getShowerMaxChance() { return Integer.parseInt(Jedis.get(uuid + ".shower_chance_max")); }

    public void setShowerMaxChance (int chance) {  Jedis.set(uuid + ".shower_chance_max", String.valueOf(chance)); }

    public int getSleepChance() { return Integer.parseInt(Jedis.get(uuid + ".sleep_chance")); }

    public void setSleepChance(int chance) {  Jedis.set(uuid + ".sleep_chance", String.valueOf(chance)); }

    public int getSleepMaxChance() { return Integer.parseInt(Jedis.get(uuid + ".sleep_chance_max")); }

    public void setSleepMaxChance (int chance) {  Jedis.set(uuid + ".sleep_chance_max", String.valueOf(chance)); }

    public int getStrengthLvl() { return Integer.parseInt(Jedis.get(uuid + ".strength")); }

    public void setStrengthLvl(int lvl) { Jedis.set(uuid + ".strength", String.valueOf(lvl)); }

    public int getAgilityLvl() { return Integer.parseInt(Jedis.get(uuid + ".agility")); }

    public void setAgilityLvl(int lvl) { Jedis.set(uuid + ".agility", String.valueOf(lvl)); }

    public int getNeedsLvl() { return Integer.parseInt(Jedis.get(uuid + ".needs")); }

    public void setNeedsLvl(int lvl) { Jedis.set(uuid + ".needs", String.valueOf(lvl)); }

    public double getHealth() { return Bukkit.getPlayer(uuid).getHealth(); }

    public void setHealth(double value) { Bukkit.getPlayer(uuid).setHealth(value); }

    public AttributeInstance getAttribute(Attribute attribute) { return Bukkit.getPlayer(uuid).getAttribute(attribute); }

    public boolean autoSell() { return Boolean.parseBoolean(Jedis.get(uuid + ".autosell")); }

    public void changeAutoSell() { Jedis.set(uuid + ".autosell", String.valueOf(!Boolean.parseBoolean(Jedis.get(uuid + ".autosell")))); }

    public Integer getSkillLvl(String type) { return Integer.parseInt(Jedis.get(uuid + ".skills." + type)); }

    public void addSkillLvl(String type) { Jedis.set(uuid + ".skills." + type, String.valueOf(getSkillLvl(type) + 1)); }

    public void setSkillLvl(String type, int lvl) { Jedis.set(uuid + ".skills." + type, String.valueOf(lvl)); }
    public Integer getAppliedSkills() { return getSkillLvl("rat") + getSkillLvl("armor") + getSkillLvl("potion") + getSkillLvl("key"); }

    public void forgetSkills() {
        setSkillLvl("rat", 0);
        setSkillLvl("armor", 0);
        setSkillLvl("potion", 0);
        setSkillLvl("key", 0);
    }

    public void updateTrainerSkills() {
        String chance = String.valueOf(TrainerSkills.getSkills(getNeedsLvl()).getNeeds_chance());
        Jedis.set(uuid + ".toilet_chance_max", chance);
        Jedis.set(uuid + ".shower_chance_max", chance);
        Jedis.set(uuid + ".sleep_chance_max", chance);
    }

    public void changeFaction(Factions faction, Boolean pay) {
        if (pay) {
            if (getMoney() >= 10000.00) {
                removeMoney(10000.00);
                Jedis.set(uuid + ".faction", faction.getName());
                if (faction.equals(Factions.NO_FACTION)) {
                    sendMessage(CValues.get("Вы успешно вышли из фракции!", 255, 255, 255).create());
                } else {
                    sendMessage(CValues.get("Вы успешно сменили фракцию!", 255, 255, 255).create());
                }
            } else {
                sendMessage(CValues.get("У вас недостаточно денег!", 255, 255, 255).create());
            }
        } else {
            Jedis.set(uuid + ".faction", faction.getName());
            sendMessage(CValues.get("Вы успешно выбрали фракцию!", 255, 255, 255).create());
        }
    }

    public void addHP(double amount) {
        if (getHealth() + amount > getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            setHealth(getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            return;
        }
        setHealth(getHealth() + amount);
    }

}

package me.d1lta.prison.events;

import static me.d1lta.prison.commands.Level.levelUp;
import static me.d1lta.prison.commands.Upgrade.checkCondition;
import static me.d1lta.prison.commands.Upgrade.getEnchants;
import static me.d1lta.prison.commands.Upgrade.getPrisonItem;
import static me.d1lta.prison.commands.Upgrade.getUpgradeButton;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import me.d1lta.prison.Main;
import me.d1lta.prison.Teleport;
import me.d1lta.prison.commands.Mine;
import me.d1lta.prison.commands.Skills;
import me.d1lta.prison.enchants.EnchantmentBook;
import me.d1lta.prison.entities.traders.ElderVillager;
import me.d1lta.prison.entities.traders.StartVillager;
import me.d1lta.prison.entities.traders.TrainerVillager;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.enums.Factions;
import me.d1lta.prison.enums.LevelBoosts;
import me.d1lta.prison.items.Apple;
import me.d1lta.prison.items.BrokenElderKey;
import me.d1lta.prison.items.ElderDust;
import me.d1lta.prison.items.ElderKey;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.EventMethods;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.LocationUtils;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClick implements Listener {

    private final Class<?> classObj = this.getClass();
    private static final Map<Component, String> methods = new HashMap<>();

    private void invokeMethod(String name, InventoryClickEvent e,  LittlePlayer pl) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        classObj.getDeclaredMethod(name, InventoryClickEvent.class, LittlePlayer.class).invoke(this, e, pl);
    }

    public static void appendMethods() {
        if (methods.isEmpty()) {
            methods.putAll(Map.of(
                    ElderVillager.title, "elderVillager",
                    StartVillager.title, "startVillager",
                    TrainerVillager.title, "trainer",
                    CValues.get("Статистика", 100, 100, 100).create(), "statistics",
                    CValues.get("Древние зачарования", 255, 255, 0).create(), "elderEnchantments",
                    CValues.get("Выбор фракции", 100, 100, 100).create(), "faction",
                    CValues.get("Уровень", 100, 100, 100).create(), "lvl",
                    CValues.get("Шахты", 100, 100, 100).create(), "mine",
                    CValues.get("Предметы", 100, 100, 100).create(), "items",
                    DComponent.create("Список улучшений."), "upgrades"
            ));
            methods.putAll(Map.of(
                    CValues.get("Древний сундук", 46, 46, 46).create(), "elderCase",
                    CValues.get("Улучшение предмета", 255, 255, 0).create(), "upgrade",
                    Skills.titleValue.create(), "skills"
            ));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        LittlePlayer pl = new LittlePlayer(e.getWhoClicked().getUniqueId());
        if (e.getInventory().getType().equals(InventoryType.CRAFTING)) {
            elderBook(e, pl);
            dustCombing(e);
        } else if (methods.containsKey(e.getView().title())) {
            invokeMethod(methods.get(e.getView().title()), e, pl);
        }
    }

    private void skills(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        int skills = LevelBoosts.get(pl.getLevel()).skills - pl.getAppliedSkills();
        if (skills == 0) { return; }
        String skill = NBT.getStringNBT(e.getCurrentItem(), "skill");
        if (NBT.getStringNBT(e.getCurrentItem(), "learned").equals("true")) { return; }
        pl.addSkillLvl(skill);
        ItemStack checker = Skills.getLearnedSkillLevel(pl.getSkillLvl(skill), pl.getSkillLvl(skill), skill);
        e.getCurrentItem().setType(checker.getType());
        e.getCurrentItem().setItemMeta(checker.getItemMeta());
    }

    private void elderCase(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
    }

    private void upgrades(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
    }

    private void items(InventoryClickEvent e, LittlePlayer pl) {
        if (CheckUtils.checkForNull(e.getCurrentItem())) {
            e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
        }
        e.setCancelled(true);
    }

    private void elderEnchantments(InventoryClickEvent e, LittlePlayer pl) {
        if (CheckUtils.checkForNull(e.getCurrentItem())) {
            e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
        }
        e.setCancelled(true);
    }

    private void statistics(InventoryClickEvent e, LittlePlayer pl) {
        if (CheckUtils.checkForNull(e.getCurrentItem())) {
            e.setCancelled(true);
        }
    }

    private void elderVillager(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        ItemStack stack = e.getCurrentItem().clone();
        int price = NBT.getIntNBT(stack, "price");
        if (price == 0) { return; }
        String type = NBT.getStringNBT(stack, "type");
        for (ItemStack it : pl.getInventory()) {
            if (it == null) { continue; }
            if (it.getItemMeta().displayName() == null) { continue; }
            if (!NBT.getStringNBT(it, "type").equals("elderstar")) { continue; }
            if (it.getAmount() >= price) {
                it.setAmount(it.getAmount() - price);
                if (type.equals("elder")) {
                    pl.giveItem(ElderKey.getKey());
                } else if (type.equals("broken_elder")) {
                    pl.giveItem(BrokenElderKey.getKey());
                }
                return;
            }
        }
        pl.sendMessage(CValues.get("У вас недостаточно звёзд!", 167, 167, 167).create());
    }

    private void startVillager(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        ItemStack stack = e.getCurrentItem();
        int price = NBT.getIntNBT(stack, "price");
        if (price == 0) { return; }
        if (pl.getMoney() >= price) {
            pl.removeMoney(price);
            switch (NBT.getStringNBT(stack, "type")) {
                case "pickaxe" -> pl.giveItem(
                        getPrisonItem(null, "pickaxe", 1, false, null));
                case "shovel" -> pl.giveItem(getPrisonItem(null, "shovel", 1, false, null));
                case "apple" -> pl.giveItem(Apple.getApple());
            }
        } else {
            pl.sendMessage("Недостаточно звёзд!");
        }
    }

    private void trainer(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        if (NBT.getStringNBT(e.getCurrentItem(), "trainer").isEmpty()) { return; }
        if (NBT.getStringNBT(e.getCurrentItem(), "maxlvl").equals("0")) { return; }
        for (String it : NBT.getKeys(e.getCurrentItem())) {
            if (it.equalsIgnoreCase("money")) {
                if (pl.getMoney() < Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "money"))) {
                    pl.sendMessage(DComponent.create("Недостаточно средств"));
                    pl.closeInventory();
                    return;
                }
            } else if (it.equalsIgnoreCase("zombie")) {
                if (pl.getZombies() < Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "zombie"))) {
                    pl.sendMessage(DComponent.create("Недостаточно зомби"));
                    pl.closeInventory();
                    return;
                }
            } else if (it.equalsIgnoreCase("bat")) {
                if (pl.getBats() < Integer.parseInt(NBT.getStringNBT(e.getCurrentItem(), "bat"))) {
                    pl.sendMessage(DComponent.create("Недостаточно мышек"));
                    pl.closeInventory();
                    return;
                }
            }
        }
        pl.removeMoney(Double.parseDouble(NBT.getStringNBT(e.getCurrentItem(), "money")));
        TrainerVillager.addLevel(pl, NBT.getStringNBT(e.getCurrentItem(), "trainer"));
        pl.closeInventory();
        TrainerVillager.openInv(pl);
    }

    private void faction(InventoryClickEvent e, LittlePlayer pl) {
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        e.setCancelled(true);
        Factions chosen = Factions.getFaction(NBT.getStringNBT(e.getCurrentItem(), "f"));
        if (Objects.equals(chosen, pl.getFaction())) { // leave from faction
            pl.changeFaction(Factions.NO_FACTION, true);
        } else if (pl.getFaction().equals(Factions.NO_FACTION)) { // no faction -> to faction
            pl.changeFaction(chosen, false);
        } else if (!pl.getFaction().equals(Factions.NO_FACTION) && !pl.getFaction().equals(chosen)) { // change faction
            pl.changeFaction(chosen, true);
        }
        pl.closeInventory();
    }

    private void lvl(InventoryClickEvent e, LittlePlayer pl) {
        if (!CheckUtils.checkForNull(e.getCurrentItem())) {
            e.setCancelled(true);
            return;
        }
        if (NBT.getStringNBT(e.getCurrentItem(), "type").equalsIgnoreCase("lvlup")) { levelUp(pl); }
    }

    private void mine(InventoryClickEvent e, LittlePlayer pl) {
        e.setCancelled(true);
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; }
        if (Mine.isAllowed(e.getCurrentItem(), pl)) {
            if (NBT.getStringNBT(e.getCurrentItem(), "nbt").equals("vault") && !pl.hasVaultAccess()) {
                pl.sendMessage(CValues.get("У вас нет доступа к подвалу!").create());
                pl.closeInventory();
                return;
            }
            pl.closeInventory();
            Teleport.tp(pl, pl.getLocation(), LocationUtils.spawnPoint(NBT.getStringNBT(e.getCurrentItem(), "nbt")));
        } else {
            pl.closeInventory();
            pl.sendMessage("вы слишком малого лвла");
        }
    }
    private void elderBook(InventoryClickEvent e, LittlePlayer pl) {
        if (!CheckUtils.checkForNull(e.getCurrentItem())) { return; } else if (!CheckUtils.checkForNull(e.getCursor())) { return; }
        if (!EnchantmentBook.isEnchantingBook(e.getCursor())) { return; }
        Set<String> keys = NBT.getKeys(e.getCursor());
        for (Enchantments it : Enchantments.values()) {
            if (!keys.contains(it.getName())) { continue; }
            if (!it.getApplicableTools().contains(e.getCurrentItem().getType())) { return; }
            if (NBT.getKeys(e.getCurrentItem()).contains(it.getName())) { return; }
            if (Integer.parseInt(NBT.getStringNBT(e.getCursor(), "chance")) < new Random().nextInt(1, 101)) {
                e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                e.setCancelled(true);
                return;
            }
            ItemStack stack = NBT.addNBT(e.getCurrentItem(), it.getName(), NBT.getIntNBT(e.getCursor(), it.getName()));
            ItemMeta meta = stack.getItemMeta();
            meta.lore(EventMethods.elderEnchantingLore(stack));
            stack.setItemMeta(meta);
            pl.getInventory().setItem(e.getSlot(), stack);
            e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
            e.setCancelled(true);
        }
    }

    private void dustCombing(InventoryClickEvent e) {
        if (!CheckUtils.checkForNull(e.getCurrentItem(), e.getCursor())) { return; }
        if (NBT.getStringNBT(e.getCursor(), "type").equals("elder_dust")) { if (EventMethods.elderDustCheck(e.getCursor(), e.getCurrentItem())) { return; } } else { return; }
        int chance = EventMethods.elderDustChance(e.getCursor(), e.getCurrentItem());
        if (chance >= 100) { chance = 100; }
        if (NBT.getStringNBT(e.getCurrentItem(), "type").equals("elder_dust")) { // combing two dusts
            e.getWhoClicked().getInventory().setItem(e.getSlot(), ElderDust.getDust(chance));
        } else if (EnchantmentBook.isEnchantingBook(e.getCurrentItem())) { // combing dust/book
            e.getWhoClicked().getInventory().setItem(e.getSlot(), EnchantmentBook.replaceChanceLore(NBT.replaceNBT(e.getCurrentItem(), "chance", String.valueOf(chance)), chance));
        } else { return; }
        e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
        e.setCancelled(true);
    }

    private void upgrade(InventoryClickEvent e, LittlePlayer pl) {
        if (!CheckUtils.checkForNull(e.getCurrentItem())) {
            e.setCancelled(true);
            return;
        }
        if (Objects.equals(e.getCurrentItem(), getUpgradeButton())) {
            String type = NBT.getStringNBT(pl.getItemInMainHand(), "type");
            int currentlvl = NBT.getIntNBT(pl.getItemInMainHand(), "level");
            if (checkCondition(pl, type, currentlvl + 1)) {
                for (String it : Main.config.getConfig().getStringList("upgrades." + type + ".level_" + currentlvl + ".requirements")) {
                    String[] parts = it.split(":");
                    if (parts[0].equalsIgnoreCase("money")) {
                        pl.removeMoney(Integer.parseInt(parts[1]));
                        break;
                    }
                }
                ItemStack clone = pl.getItemInMainHand().clone();
                pl.getItemInMainHand().setAmount(0);
                pl.getInventory().setItem(pl.getHeldItemSlot(), getPrisonItem(pl, type, currentlvl + 1, false, getEnchants(clone)));
                pl.sendMessage("Предмет улучшен!");
                pl.closeInventory();
            } else {
                pl.sendMessage("Требования не выполнены!");
                pl.closeInventory();
            }
        }
        e.setCancelled(true);
    }
}


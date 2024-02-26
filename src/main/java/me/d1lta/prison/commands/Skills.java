package me.d1lta.prison.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Skills implements CommandExecutor {

    // Крысы | Оружейник | Зелье | Ключи

    public static final CValues titleValue = CValues.get("Скиллы", 100, 100, 100);
    private static final Map<Integer, String> startPosOfTypes = Map.of(1, "rat", 3, "armor", 5, "potion", 7, "key");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!CheckUtils.checkForPlayer(sender)) { return false; }
        openInv(new LittlePlayer(((Player) sender).getUniqueId()));
        return false;
    }

    public static void openInv(LittlePlayer pl) {
        Inventory inventory = Bukkit.createInventory(null, 45, titleValue.create());
        addSkills(inventory, pl);
        setFillers(inventory);
        pl.openInventory(inventory);
    }

    private static void setFillers(Inventory inv) {
        ItemStack stack = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(CValues.get("").create());
        stack.setItemMeta(meta);
        List.of(0, 2, 4, 6, 8).forEach( it -> {
            for (int i = 0; i <= 4; i++) {
                inv.setItem(it + (9 * i), stack);
            }
        });
    }

    private static void addSkills(Inventory inv, LittlePlayer pl) {
        for (Entry<Integer, String> entry : startPosOfTypes.entrySet()) {
            Integer k = entry.getKey(); // slot
            String v = entry.getValue(); // type
            inv.setItem(k, getSkillIcon(v));
            for (int i = 1; i <= 4; i++) {
                getSkillIcon(v);
                inv.setItem(k + (9 * i), getLearnedSkillLevel(pl.getSkillLvl(v), i, v));
            }
        }
    }

    public static ItemStack getLearnedSkillLevel(int playerLvl, int requiredLvl, String skill) {
        ItemStack stack = new ItemStack(getLearnedSkillIcon(playerLvl >= requiredLvl));
        ItemMeta meta = stack.getItemMeta();
        Map<String, String> nbt = new HashMap<>();
        if (playerLvl >= requiredLvl) {
            meta.displayName(CValues.get("Изучено").create());
            nbt.put("learned", "true");
        } else {
            meta.displayName(CValues.get("Не изучено").create());
            nbt.put("learned", "false");
        }
        nbt.put("skill", skill);
        stack.setItemMeta(meta);
        for (Entry<String, String> entry : nbt.entrySet()) {
            stack = NBT.addNBT(stack, entry.getKey(), entry.getValue());
        }
        return stack;
    }

    private static Material getLearnedSkillIcon(boolean b) {
        if (b) {
            return Material.LIME_STAINED_GLASS_PANE;
        } else {
            return Material.RED_STAINED_GLASS_PANE;
        }
    }

    private static ItemStack getSkillIcon(String type) {
        ItemStack stack = new ItemStack(getIcon(type));
        ItemMeta meta = stack.getItemMeta();
        meta.displayName(getDisplayName(type).create());
        meta.lore(DComponent.createList(getLore(type)));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS);
        stack.setItemMeta(meta);
        return stack;
    }

    private static Material getIcon(String type) {
        switch (type) {
            case "rat" -> { return Material.DRIED_KELP; }
            case "armor" -> { return Material.IRON_CHESTPLATE; }
            case "potion" -> { return Material.SPLASH_POTION; }
            case "key" -> { return Material.GHAST_TEAR; }
            default -> { return Material.BEDROCK; }
        }
    }

    private static CValues getDisplayName(String type) {
        switch (type) {
            case "rat" -> { return CValues.get("Крысолов", 255, 170, 80); }
            case "armor" -> { return CValues.get("Оружейник", 255, 170, 80); }
            case "potion" -> { return CValues.get("Зельевар", 255, 170, 80); }
            case "key" -> { return CValues.get("Копатель", 255, 170, 80); }
            default -> { return CValues.get("err", 255, 0, 0); }
        }
    }

    private static CValues[] getLore(String type) {
        switch (type) {
            case "rat" -> { return new CValues[] {
                    CValues.get("Вы будете получать больше"),
                    CValues.get("крыс с определённым шансом."),
                    CValues.get("Чем больше уровень навыка,"),
                    CValues.get("тем больше шанс.")
            }; }
            case "armor" -> { return new CValues[] {
                    CValues.get("Вы научитесь улучшать мечи"),
                    CValues.get("и броню до алмазного уровня."),
                    CValues.get("Чем больше уровень навыка,"),
                    CValues.get("тем прочнее будут предметы."),
            }; }
            case "potion" -> { return new CValues[] {
                    CValues.get("Навык зельеварства позволит"),
                    CValues.get("вам создавать различные зелья."),
                    CValues.get("С повышением уровня навыка у вас"),
                    CValues.get("будет больше разнообразия в создании."),
            }; }
            case "key" -> { return new CValues[] {
                    CValues.get("Получаете мало ключей?"),
                    CValues.get("С этим навыком вы будете получать"),
                    CValues.get("гораздо больше ключей при копании."),
                    CValues.get("Теперь вы сможете чаще открывать кейсы!")
            }; }
            default -> { return new CValues[] {}; }
        }
    }























}

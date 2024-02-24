package me.d1lta.prison.chests;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import me.d1lta.prison.Main;
import me.d1lta.prison.enums.Enchantments;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ElderChest implements Listener {

    private static final int countOfBooks = 9;

    public void elderCase(Boolean advanced, LittlePlayer pl) {
        Inventory inv = Bukkit.createInventory(null, 27, CValues.get("Древний сундук", 46, 46, 46).create());
        ItemStack[] contents = inv.getContents();
        for (int i = 0; i < countOfBooks; i++) {
            contents[i + 9] = Enchantments.values()[new Random().nextInt(1, Enchantments.values().length)].getBook(advancedLvl(advanced), chance(advanced)).getBook();
        }
        contents = addFillers(contents.clone());
        contents[22] = new ItemStack(arrow());
        inv.setContents(contents);
        pl.openInventory(inv);
        contents = null;
        AtomicInteger i = new AtomicInteger(0);

        new BukkitRunnable() {

            @Override
            public void run() {
                if (i.get() < 18) {
                    ItemStack[] contents = inv.getContents();
                    for (int j = 0; j < 9; j++) {
                        if (j != 8) {
                            contents[9 + j] = inv.getItem(10 + j);
                            continue;
                        }
                        contents[17] = inv.getItem(9);
                    }
                    contents = addFillers(contents.clone());
                    contents[22] = new ItemStack(arrow());
                    inv.setContents(contents);
                    pl.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    i.set(i.get() + 1);
                } else {
                    pl.giveItem(inv.getItem(13));
                    EnderChest.opening.remove(pl.uuid);
                    Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).forEach(it -> new LittlePlayer(it).sendMessage(
                            DComponent.create(
                                    CValues.get(pl.getName(), pl.getFaction().getColor()),
                                    CValues.get(" достал из древнего сундука ", 255, 255, 255),
                                    CValues.get(inv.getItem(13).getItemMeta().lore().get(0), Enchantments.getEnchantment(NBT.getStringNBT(inv.getItem(13), "type")).getTextColor()),
                                    CValues.get("!", 255, 255, 255)
                            )));
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 5L, 5L);
    }

    private static ItemStack[] addFillers(ItemStack[] arr) {
        for (int j = 0; j < 9; j++) {
            arr[j] = new ItemStack(filler());
            arr[j+18] = new ItemStack(filler());
        }
        return arr;
    }

    private static ItemStack arrow() {
        ItemStack arrow = new ItemStack(Material.LEVER);
        ItemMeta meta = arrow.getItemMeta();
        meta.displayName(DComponent.create(""));
        arrow.setItemMeta(meta);
        return arrow;
    }

    private static ItemStack filler() {
        ItemStack filler;
        switch (new Random().nextInt(1,10)) {
            case 1 -> filler = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            case 2 -> filler = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
            case 3 -> filler = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            case 4 -> filler = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            case 5 -> filler = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            case 6 -> filler = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            case 7 -> filler = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            case 8 -> filler = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
            case 9 -> filler = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
            default -> filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        }
        ItemMeta meta = filler.getItemMeta();
        meta.displayName(DComponent.create(""));
        filler.setItemMeta(meta);
        return filler;
    }

    private static int chance(Boolean a) {
        int r = 0;
        if (a) {
            r = new Random().nextInt(300, 600);
        } else {
            r = new Random().nextInt(0, 501);
        }
        if (r < 300) {
            return new Random().nextInt(1,25);
        } else if (r < 400) {
            return new Random().nextInt(25, 50);
        } else if (r < 450) {
            return new Random().nextInt(25,75);
        } else {
            return new Random().nextInt(25,101);
        }
    }

    private static int advancedLvl(Boolean a) {
        if (a) {
            return 3;
        } else {
            int r = new Random().nextInt(1,11);
            if (r < 5) {
                return 1;
            } else if (r < 8) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}

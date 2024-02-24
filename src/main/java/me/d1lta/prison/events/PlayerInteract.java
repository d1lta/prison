package me.d1lta.prison.events;

import static me.d1lta.prison.chests.DefaultChest.loc;
import static me.d1lta.prison.chests.DefaultChest.openCaseUI;
import static me.d1lta.prison.chests.EnderChest.openChest;
import static me.d1lta.prison.chests.EnderChest.opening;
import static me.d1lta.prison.items.VaultAccess.getAccess;

import me.d1lta.prison.items.Key;
import me.d1lta.prison.utils.CheckUtils;
import me.d1lta.prison.utils.DComponent;
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.utils.NBT;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        LittlePlayer pl = new LittlePlayer(e.getPlayer().getUniqueId());
        if (e.getPlayer().getInventory().getItemInMainHand().equals(getAccess())) { onVaultAccess(e, pl); }
        onToiletPaper(e, pl);
        onSleep(e, pl);
        onCase(e, pl);
        onEnderChest(e, pl);
    }

    private void onVaultAccess(PlayerInteractEvent e, LittlePlayer pl) {
        if (!pl.hasVaultAccess()) {
            e.getPlayer().getInventory().getItemInMainHand().setAmount(0);
            pl.giveVaultAccess();
            pl.sendMessage("Теперь вы можете попасть в подвал");
        } else {
            e.setCancelled(true);
            pl.dropItem();
            pl.sendMessage("От осознания что у вас уже есть доступ в подвал, вы случайно выронили книгу!");
        }
    }

    private void onToiletPaper(PlayerInteractEvent e, LittlePlayer pl) {
        if (!CheckUtils.checkForNull(e.getItem())) { return; }
        if (NBT.getKeys(e.getItem()).contains("toiletpaper") && pl.getToiletStatus()) {
            pl.removeEffect(PotionEffectType.SLOW);
            pl.sendMessage(DComponent.create("Наконец-то я посрал!", TextColor.color(249, 254, 255)));
            pl.setToiletStatus(false);
            ItemStack stack = e.getItem();
            e.getPlayer().getInventory().getItemInMainHand().setAmount(stack.getAmount() - 1);
        }
    }

    private void onSleep(PlayerInteractEvent e, LittlePlayer pl) {
        if (e.getClickedBlock() == null) { return; }
        if (e.getClickedBlock().getType().equals(Material.WHITE_BED) && pl.getSleepStatus()) {
            pl.setSleepStatus(false);
            pl.removeEffect(PotionEffectType.SLOW_DIGGING);
            pl.sendMessage(DComponent.create("Мне приснилось как я копал. Какой ужас!", TextColor.color(249, 254, 255)));
        }
    }

    private void onCase(PlayerInteractEvent e, LittlePlayer pl) {
        if (e.getClickedBlock() == null) { return; }
        if (!e.getClickedBlock().getLocation().equals(loc) || !e.getClickedBlock().getType().equals(Material.CHEST)) { return; }
        e.setCancelled(true);
        if (!e.getPlayer().getInventory().getItemInMainHand().isSimilar(Key.getKey())) { return; }
        pl.getItemInMainHand().setAmount(pl.getItemInMainHand().getAmount() - 1);
        openCaseUI(pl);
    }

    private void onEnderChest(PlayerInteractEvent e, LittlePlayer pl) {
        if (e.getClickedBlock() == null) { return; }
        if (!e.getClickedBlock().getLocation().equals(loc) && !e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) { return; }
        ItemStack stack = pl.getItemInMainHand();
        if (!CheckUtils.checkForNull(stack)) { return; }
        if (opening.contains(pl.uuid)) {
            pl.sendMessage(CValues.get("Дождитесь открытие древнего кейса!", 255, 172, 0).create());
            return;
        }
        if (NBT.getStringNBT(stack, "type").equals("elder_key")) {
            e.setCancelled(true);
            openChest(pl, true, stack);
        } else if (NBT.getStringNBT(stack, "type").equals("broken_elder_key")) {
            e.setCancelled(true);
            openChest(pl, false, stack);
        }
    }

}

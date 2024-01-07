package me.d1lta.prison.mines;

import me.d1lta.prison.Main;
import me.d1lta.prison.mines.mine.ConcreteMine;
import me.d1lta.prison.mines.mine.DesertMine;
import me.d1lta.prison.mines.mine.DirtMine;
import me.d1lta.prison.mines.mine.EndMine;
import me.d1lta.prison.mines.mine.HellMine;
import me.d1lta.prison.mines.mine.IceMine;
import me.d1lta.prison.mines.mine.ObsMine;
import me.d1lta.prison.mines.mine.QuarryMine;
import me.d1lta.prison.mines.mine.QuartzMine;
import me.d1lta.prison.mines.mine.SpiderMine;
import me.d1lta.prison.mines.mine.StoneMine;
import me.d1lta.prison.mines.mine.VaultDirtMine;
import me.d1lta.prison.mines.mine.VaultJewelryMine;
import org.bukkit.Bukkit;

public class MinesTimer {

    int i = 0;

    public void timer() {
        DirtMine.fill();
        StoneMine.fill();
        ConcreteMine.fill();
        HellMine.fill();
        DesertMine.fill();
        QuartzMine.fill();
        EndMine.fill();
        SpiderMine.fill();
        QuarryMine.fill();
        IceMine.fill();
        ObsMine.fill();
        VaultDirtMine.fill();
        VaultJewelryMine.fill();
        Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            i++;
            if (i % 180 == 0) {
                DirtMine.fill();
            }
            if (i % 420 == 0) {
                StoneMine.fill();
            }
            if (i % 180 == 0) {
                ConcreteMine.fill();
            }
            if (i % 240 == 0) {
                HellMine.fill();
            }
            if (i % 360 == 0) {
                DesertMine.fill();
            }
            if (i % 540 == 0) {
                QuartzMine.fill();
            }
            if (i % 480 == 0) {
                EndMine.fill();
            }
            if (i % 320 == 0) {
                SpiderMine.fill();
            }
            if (i % 900 == 0) {
                QuarryMine.fill();
            }
            if (i % 2400 == 0) {
                IceMine.fill();
            }
            if (i % 3000 == 0) {
                ObsMine.fill();
            }
            if (i % 300 == 0) {
                VaultDirtMine.fill();
            }
            if (i % 600 == 0) {
                VaultJewelryMine.fill();
            }
        }, 20L, 20L);
    }

}

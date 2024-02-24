package me.d1lta.prison.boosters;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.d1lta.prison.utils.LittlePlayer;
import me.d1lta.prison.warzone.BlocksPoint;
import me.d1lta.prison.warzone.WarzoneCapture;

public class BlockBoostHandler {

    private static final Map<UUID, Integer> blocks = new HashMap<>();

    public static double getBlocksMultiplier(LittlePlayer pl) {
        double multiplier = 1;
        if (pl.getFaction().equals(WarzoneCapture.blocks.capturedBy)) { // warzone booster
            multiplier += BlocksPoint.multiplier - 1; // 1.2
        }
        if (multiplier < 1) { multiplier = 1; }
        return multiplier;
    }

    public static void add(LittlePlayer pl, int amount) {
        if (!blocks.containsKey(pl.uuid)) {
            blocks.put(pl.uuid, 1);
        } else {
            blocks.put(pl.uuid, blocks.get(pl.uuid) + amount);
        }
    }

    public static double get(LittlePlayer pl) {
        return blocks.get(pl.uuid);
    }

    public static void applyToDB(LittlePlayer pl) {
        pl.addBlocks((int) ((blocks.get(pl.uuid) * getBlocksMultiplier(pl)) - blocks.get(pl.uuid)));
        blocks.remove(pl.uuid);
    }

    public static void applyToDBEveryone() {
        if (blocks.isEmpty()) { return; }
        for(UUID uuid: blocks.keySet()) {
            applyToDB(new LittlePlayer(uuid));
        }
    }

}

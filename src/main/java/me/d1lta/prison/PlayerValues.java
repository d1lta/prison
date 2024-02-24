package me.d1lta.prison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import me.d1lta.prison.mines.AllowedBlocks;
import org.bukkit.Material;

public class PlayerValues {

    public static Map<UUID, Integer> blocks = new HashMap<>();
    public static Map<UUID, Double> money = new HashMap<>();
    public static Map<UUID, BlockList> certainBlocks = new HashMap<>();

    public static void setValues(UUID uuid) {
        putBlocks(uuid, Integer.valueOf(Jedis.get(String.format("%s.blocks", uuid))));
        putMoney(uuid, Double.valueOf(Jedis.get(String.format("%s.money", uuid))));
        AllowedBlocks.blockMats.forEach(it -> putCertainBlock(uuid, it, Integer.valueOf(Jedis.get(String.format("%s.blocks.%s", uuid, it.name().toLowerCase())))));
    }

    public static void save() {
        blocks.forEach((k,v) -> Jedis.set(String.format("%s.blocks", k), String.valueOf(v)));
        money.forEach((k,v) -> Jedis.set(String.format("%s.money", k), String.valueOf(v)));
        for (Entry<UUID, BlockList> entry : certainBlocks.entrySet()) {
            entry.getValue().blockCollection.forEach(it -> Jedis.set(String.format("%s.blocks.%s", entry.getKey(), it.material.name().toLowerCase()), String.valueOf(it.amount)));
        }
    }

    public static void save(UUID uuid) {
        Jedis.set(String.format("%s.blocks", uuid), String.valueOf(blocks.get(uuid)));
        blocks.remove(uuid);

        Jedis.set(String.format("%s.money", uuid), String.valueOf(money.get(uuid)));
        money.remove(uuid);

        certainBlocks.get(uuid).blockCollection.forEach(it -> Jedis.set(String.format("%s.blocks.%s", uuid, it.material.name().toLowerCase()), String.valueOf(it.amount)));
        certainBlocks.remove(uuid);
    }

    public static void putBlocks(UUID uuid, Integer amount) {
        blocks.putIfAbsent(uuid, amount);
    }

    public static void addBlocks(UUID uuid, Integer amount) { blocks.replace(uuid, blocks.get(uuid) + amount); }

    public static void addBlock(UUID uuid) {
        blocks.replace(uuid, blocks.get(uuid) + 1);
    }

    public static Integer getBlocks(UUID uuid) {
        return blocks.get(uuid);
    }

    public static void putMoney(UUID uuid, Double amount) {
        money.putIfAbsent(uuid, amount);
    }

    public static void addMoney(UUID uuid, Double amount) { money.replace(uuid, money.get(uuid) + amount); }

    public static void addMoney(UUID uuid) { money.replace(uuid, money.get(uuid) + 1); }

    public static Double getMoney(UUID uuid) { return money.get(uuid); }

    public static void removeMoney(UUID uuid, double amount) { money.replace(uuid, money.get(uuid) - amount);}

    public static void putCertainBlock(UUID uuid, Material material, Integer amount) {
        if (!certainBlocks.containsKey(uuid)) { certainBlocks.put(uuid, new BlockList()); }
        certainBlocks.get(uuid).add(CertainBlock.create(material, amount));
    }

    public static void setCertainBlocks(UUID uuid, Material material, Integer amount) { certainBlocks.get(uuid).add(CertainBlock.create(material, amount)); }

    public static void addCertainBlock(UUID uuid, Material material, Integer amount) { certainBlocks.get(uuid).add(CertainBlock.create(material, amount)); }

    public static void addCertainBlock(UUID uuid, Material material) { certainBlocks.get(uuid).add(CertainBlock.create(material, certainBlocks.get(uuid).get(material) + 1));}

    public static Integer getCertainBlock(UUID uuid, Material material) { return certainBlocks.get(uuid).get(material); }

    public static class BlockList {

        List<CertainBlock> blockCollection;

        public BlockList() {
            this.blockCollection = new ArrayList<>();
        }

        public void add(CertainBlock block) {
            for (CertainBlock it : blockCollection) {
                if (it.material.equals(block.material)) {
                    this.blockCollection.remove(it);
                    break;
                }
            }
            this.blockCollection.add(block);
        }

        public Integer get(Material material) {
            for (CertainBlock cb: blockCollection) {
                if (cb.material.equals(material)) {
                    return cb.amount;
                }
            }
            return 0;
        }

    }

    public static class CertainBlock {

        Material material;
        int amount;

        public CertainBlock(Material material, Integer amount) {
            this.material = material;
            this.amount = amount;
        }

        public static CertainBlock create(Material material, Integer amount) {
            return new CertainBlock(material, amount);
        }
    }
}

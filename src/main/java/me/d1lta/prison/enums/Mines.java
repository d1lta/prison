package me.d1lta.prison.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import me.d1lta.prison.mines.mine.ConcreteMine;
import me.d1lta.prison.mines.mine.DesertMine;
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
import me.d1lta.prison.utils.DComponent.CValues;
import me.d1lta.prison.utils.Translate;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public enum Mines {

    STONEMINE(Material.STONE, "stonemine", 3),
    CONCRETE(Material.BRICKS, "concrete", 5),
    HELL(Material.NETHERRACK, "hell", 7),
    DESERT(Material.SANDSTONE, "desert", 9),
    QUARTZMINE(Material.PRISMARINE, "quartzmine", 12),
    END(Material.END_STONE_BRICKS, "end", 14),
    SPIDER(Material.COBWEB, "spider", 16),
    QUARRY(Material.TERRACOTTA, "quarry", 19),
    ICEHILLS(Material.ICE, "icehills", 21),
    OBSMINE(Material.OBSIDIAN, "obsmine", 23),
    VAULT(Material.KNOWLEDGE_BOOK, "vault", 5);

    public final Material material;
    public final String world;
    public final int lvl;
    public final List<CValues> lore;

    Mines(Material material, String world, int lvl) {
        this.material = material;
        this.world = world;
        this.lvl = lvl;
        this.lore = lore();
    }

    public List<Component> getLoreComponents() {
        List<Component> list = new ArrayList<>();
        this.lore.forEach(it -> list.add(it.create()));
        return list;
    }

    private List<CValues> lore() {
        List<CValues> list = new ArrayList<>();
        list.add(CValues.get(""));
        list.add(CValues.get("Блоки"));
        for (Material it : getBlocks(this.world)) {
            if (Objects.equals(Material.LAVA, it)) { continue; }
            list.add(CValues.get(String.format("▪ %s", Translate.block(it))));
        }
        list.add(CValues.get(""));
        list.add(CValues.get("Инструменты"));
        getInstruments(this.world).forEach(it -> list.add(CValues.get(String.format("▪ %s", it))));
        list.add(CValues.get(""));
        list.add(CValues.get(String.format("Доступно с %d-го уровня.", this.lvl)));
        return list;
    }


    private List<Material> getBlocks(String world) {
        switch (world) {
            case "stonemine" -> { return StoneMine.blocks.values().stream().toList(); }
            case "concrete" -> { return ConcreteMine.blocks.values().stream().toList(); }
            case "hell" -> { return HellMine.blocks.values().stream().toList(); }
            case "desert" -> { return DesertMine.blocks.values().stream().toList(); }
            case "quartzmine" -> { return QuartzMine.blocks.values().stream().toList(); }
            case "end" -> { return EndMine.blocks.values().stream().toList(); }
            case "spider" -> { return SpiderMine.blocks.values().stream().toList(); }
            case "quarry" -> { return QuarryMine.blocks.values().stream().toList(); }
            case "icehills" -> { return IceMine.blocks.values().stream().toList(); }
            case "obsmine" -> { return ObsMine.blocks.values().stream().toList(); }
            case "vault" -> {
                List<Material> list = new ArrayList<>(VaultDirtMine.blocks.values().stream().toList());
                list.addAll(VaultJewelryMine.blocks.values());
                return list.stream().toList();
            }
            default -> { return List.of(Material.BARRIER); }
        }
    }

    private List<String> getInstruments(String world) {
        switch (world) {
            case "stonemine", "desert", "quartzmine", "end", "quarry", "icehills", "obsmine" -> { return List.of("Кирка"); }
            case "concrete", "hell", "vault" -> { return List.of("Кирка", "Лопата"); }
            case "spider" -> { return List.of("Ножницы"); }
            default -> { return List.of("NULL"); }
        }
    }
}



















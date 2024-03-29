package me.d1lta.prison.enums;

public enum TrainerSkills {

    LEVEL_0(0, 1.0, 0.0, 4000),
    LEVEL_1(1, 1.05, 1.5, 4250),
    LEVEL_2(2, 1.1, 3.0, 4500),
    LEVEL_3(3, 1.15, 4.5, 5000),
    LEVEL_4(4, 1.2, 6, 5500),
    LEVEL_5(5, 1.25, 7.5, 6000),
    LEVEL_6(6, 1.3, 9, 6250),
    LEVEL_7(7, 1.35, 10.5, 6500),
    LEVEL_8(8, 1.4, 12, 7000),
    LEVEL_9(9, 1.45, 13.5, 7500),
    LEVEL_10(10, 1.5, 15, 8000);

    private final int lvl;
    private final double strength_boost;
    private final double agility_boost;
    private final int needs_chance;

    TrainerSkills(int lvl, double strength_boost, double agility_boost, int needs_chance) {
        this.lvl = lvl;
        this.strength_boost = strength_boost;
        this.agility_boost = agility_boost;
        this.needs_chance = needs_chance;
    }

    public double getStrength_boost() {
        return strength_boost;
    }

    public double getAgility_boost() {
        return agility_boost;
    }

    public static TrainerSkills getSkills(int lvl) {
        for(TrainerSkills it: TrainerSkills.values()) {
            if (it.lvl == lvl) {
                return it;
            }
        }
        return LEVEL_0;
    }

    public int getNeeds_chance() {
        return needs_chance;
    }
}

package me.d1lta.prison.enums;

import me.d1lta.prison.utils.LittlePlayer;

public enum LevelBoosts {

    LEVEL_1(1, 0, 0),
    LEVEL_2(2, 1, 0),
    LEVEL_3(3, 2, 1),
    LEVEL_4(4, 3, 1),
    LEVEL_5(5, 4, 1),
    LEVEL_6(6, 5, 1),
    LEVEL_7(7, 6, 2),
    LEVEL_8(8, 7, 2),
    LEVEL_9(9, 8, 2),
    LEVEL_10(10, 9, 3),
    LEVEL_11(11, 10, 3),
    LEVEL_12(12, 11, 3),
    LEVEL_13(13, 12, 3),
    LEVEL_14(14, 13, 3),
    LEVEL_15(15, 14, 4),
    LEVEL_16(16, 15, 4),
    LEVEL_17(17, 16, 4),
    LEVEL_18(18, 17, 5),
    LEVEL_19(19, 18, 5),
    LEVEL_20(20, 19, 5),
    LEVEL_21(21, 20, 5),
    LEVEL_22(22, 21, 5),
    LEVEL_23(23, 22, 5),
    LEVEL_24(24, 23, 6),
    LEVEL_25(25, 24, 6);

    private final int lvl;
    private final int health;
    public final int skills;

    LevelBoosts(int lvl, int health, int skills) {
        this.lvl = lvl;
        this.health = health;
        this.skills = skills;
    }

    public void applyToPlayer(LittlePlayer pl) {
        pl.setMaxHP(20 + this.health);
    }

    public static LevelBoosts get(int lvl) {
        for (LevelBoosts l: LevelBoosts.values()) {
            if (l.lvl == lvl) {
                return l;
            }
        }
        return null;
    }

}

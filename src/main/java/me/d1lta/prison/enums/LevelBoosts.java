package me.d1lta.prison.enums;

import me.d1lta.prison.utils.LittlePlayer;

public enum LevelBoosts {

    LEVEL_1(1, 0),
    LEVEL_2(2, 1),
    LEVEL_3(3, 2),
    LEVEL_4(4, 3),
    LEVEL_5(5, 4),
    LEVEL_6(6, 5),
    LEVEL_7(7, 6),
    LEVEL_8(8, 7),
    LEVEL_9(9, 8),
    LEVEL_10(10, 9),
    LEVEL_11(11, 10),
    LEVEL_12(12, 11),
    LEVEL_13(13, 12),
    LEVEL_14(14, 13),
    LEVEL_15(15, 14),
    LEVEL_16(16, 15),
    LEVEL_17(17, 16),
    LEVEL_18(18, 17),
    LEVEL_19(19, 18),
    LEVEL_20(20, 19),
    LEVEL_21(21, 20),
    LEVEL_22(22, 21),
    LEVEL_23(23, 22),
    LEVEL_24(24, 23),
    LEVEL_25(25, 24);

    private final int lvl;
    private final int health;

    LevelBoosts(int lvl, int health) {
        this.lvl = lvl;
        this.health = health;
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

package me.d1lta.prison.utils;

public class RomanNumeration {

    public static String get(int num) {
        switch (num) {
            case 1 -> { return "I"; }
            case 2 -> { return "II"; }
            case 3 -> { return "III"; }
            case 4 -> { return "IV"; }
            case 5 -> { return "V"; }
            default -> { return "0"; }
        }
    }

}

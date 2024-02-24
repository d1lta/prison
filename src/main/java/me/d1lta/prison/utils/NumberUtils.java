package me.d1lta.prison.utils;

import me.d1lta.prison.Main;

public class NumberUtils {

    public static String withK(int i) {
        if (i >= 1000 && i < 1000000) {
            return String.format("%.2f", (i / 1000.00)) + "K";
        }
        if (i >= 1000000) {
            return String.format("%.2f", (i / 1000000.00)) + "M";
        }
        return String.valueOf(i);
    }

    public static String getRomanNumber(int num) {
        switch (num) {
            case 1 -> { return "I"; }
            case 2 -> { return "II"; }
            case 3 -> { return "III"; }
            case 4 -> { return "IV"; }
            case 5 -> { return "V"; }
            default -> { return "0"; }
        }
    }

    public static Double getValueD(String path, String value) {
        return Double.parseDouble(String.valueOf(Main.config.getConfig().get(path + value)));
    }

    public static Float getValueF(String path, String value) {
        return Float.parseFloat(String.valueOf(Main.config.getConfig().get(path + value)));
    }

}



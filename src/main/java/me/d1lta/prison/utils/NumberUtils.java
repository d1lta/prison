package me.d1lta.prison.utils;

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

}

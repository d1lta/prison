package me.d1lta.prison.utils;

import me.d1lta.prison.Main;

public class ConfigUtils {

    public static String getString(String path) {
        return Main.config.getConfig().getString(path);
    }

}

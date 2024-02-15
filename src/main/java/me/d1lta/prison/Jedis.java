package me.d1lta.prison;

public class Jedis {

    public static void set(String a, String b) {
        try (redis.clients.jedis.Jedis jedis = Main.pool.getResource()) {
            jedis.set(a, b);
        }
    }

    public static String get(String a) {
        try (redis.clients.jedis.Jedis jedis = Main.pool.getResource()) {
            return jedis.get(a);
        }
    }
}

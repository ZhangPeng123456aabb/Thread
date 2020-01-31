package com.exam.redis;

public class JedisConstants {
    public static final String OK = "OK";
    public static final Long OK_LONG = 1L;

    /**
     * NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
     **/
    public static final String NOT_EXIST = "NX";
    public static final String EXIST = "XX";

    /**
     * expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     **/
    public static final String SECONDS = "EX";
    public static final String MILLISECONDS = "PX";

    /**
     * atomic del lua script
     */
    public static String ATOMIC_DEL_LUA_SCRIPT
        = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";

}

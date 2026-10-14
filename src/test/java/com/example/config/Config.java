package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private Config() { }   // utility class: nobody should create one

    /** A -D system property wins over the file, e.g. -Dbrowser=chrome */
    public static String get(String key) {
        String override = System.getProperty(key);
        return override != null ? override : PROPS.getProperty(key);
    }

    public static String baseUrl()        { return get("base.url"); }
    public static String browser()        { return get("browser"); }
    public static boolean headless()      { return Boolean.parseBoolean(get("headless")); }
    public static int timeoutSeconds()    { return Integer.parseInt(get("timeout.seconds")); }
}
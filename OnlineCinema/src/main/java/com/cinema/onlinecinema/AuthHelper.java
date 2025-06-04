package com.cinema.onlinecinema;
import java.util.prefs.Preferences;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class AuthHelper {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String NODE_PATH = "/com/myapp/auth";
    static Preferences prefs = Preferences.userRoot().node(NODE_PATH);
    public static void saveCredentials(String login, String token) {
        try {
            prefs.put(KEY_USERNAME, SecureAuthStorage.encrypt(login));
            prefs.put(KEY_PASSWORD, SecureAuthStorage.encrypt(token));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String[] loadCredentials() {
        try {
            Preferences prefs = Preferences.userRoot().node(NODE_PATH);
            String username = SecureAuthStorage.decrypt(prefs.get(KEY_USERNAME, null));
            String token = SecureAuthStorage.decrypt(prefs.get(KEY_PASSWORD, null));

            return new String[]{username, token};
        } catch (Exception e) {
            return null;
        }
    }
    public static void clearCredentials() {
        Preferences prefs = Preferences.userRoot().node(NODE_PATH);
        prefs.remove(KEY_USERNAME);
        prefs.remove(KEY_PASSWORD);
    }
}


package com.cinema.onlinecinema;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionChecker {
    public static boolean isServerAvailable(String targetUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(targetUrl).openConnection();
            connection.setConnectTimeout(5000); // 5 сек
            connection.setReadTimeout(5000);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 500;
        } catch (Exception e) {
            return false;
        }
    }
}

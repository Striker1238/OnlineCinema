package com.cinema.onlinecinema;
import Model.Movie;
import Model.Profile;
import Model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.function.Consumer;

public class Client {
    public static User userData;
    public static Profile profileData;
    public static String serverUrl = "http://192.168.141.12:8080/";//"http://localhost:8080/";
    static HttpClient client;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void StartClient(){
        try{
            client = HttpClient.newHttpClient();
        }catch (Exception e){
            System.out.println("Error in client initialization! Message: " + e.getMessage());
        }
    }
    public static <T> void sendRequestAsync(HttpRequest request, TypeReference<T> typeRef,Consumer<T> onSuccess) {
        Client.client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(json -> parseJson(json, typeRef))
                .thenAccept(result -> {
                    Platform.runLater(() -> onSuccess.accept(result));
                })
                .exceptionally(e -> {
                    System.err.println("Error sending request, check your internet connection! Message: " + e.getMessage());
                    return null;
                });
    }
    private static <T> T parseJson(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Failed to parse JSON to type " + typeRef.getType().getTypeName(), e);
        }
    }
    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to convert object from JSON: " + e.getMessage());
            return null;
        }
    }

    public static class MovieResponse <T extends Movie> {
        @JsonProperty("movies")
        public List<T> movies;
        @JsonProperty("nextCursor")
        public long nextCursor;
        @JsonCreator
        public MovieResponse(
                @JsonProperty("movies") List<T> movies,
                @JsonProperty("nextCursor") long nextCursor) {
            this.movies = movies;
            this.nextCursor = nextCursor;
        }
    }
}

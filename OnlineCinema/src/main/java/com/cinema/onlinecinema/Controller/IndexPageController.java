package com.cinema.onlinecinema.Controller;
import Model.LightweightMovie;
import Model.Movie;
import com.cinema.onlinecinema.Client;
import com.cinema.onlinecinema.StartApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Random;

public class IndexPageController {
    private long cursor = 0;
    private final int pageSize = 5;
    private boolean loading = false;

    @FXML
    private TextField searchField;

    @FXML
    private Button notificationsButton, settingsButton;

    @FXML
    private Button homeButton, bookmarksButton, profileButton;

    @FXML
    private FlowPane cardContainer;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        cardContainer.getChildren().clear();
        loadMore();

        scrollPane.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= 0.90 && !loading && cursor != -1) {
                loadMore();
            }
        });
    }
    private void loadMore() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Client.serverUrl +"api/media?afterId=" + cursor + "&limit=" + pageSize))
                .header("Accept", "application/json")
                .GET()
                .build();

        loading = true;
        Client.sendRequestAsync(request,
                new TypeReference<Client.MovieResponse<LightweightMovie>>(){},
                response -> {
                    if (response.movies.isEmpty()) {
                        addMessageNoMoreMovies();
                        cursor = -1;
                    } else {
                        for (Movie movie : response.movies) {
                            MovieCardFactory.addMovieCard((LightweightMovie) movie,cardContainer);
                        }
                        cursor = response.nextCursor;

                        if (response.movies.size() < pageSize) {
                            cursor = -1;
                            addMessageNoMoreMovies();
                        }
                    }
                    loading = false;
                });
    }
    private void addMessageNoMoreMovies() {
        String[] messages = {
                "Больше фильмов нет... Может, сходим в реальный кинотеатр? ",
                "Контента больше нет...",
                "Здесь должно быть кино, но котики заняли всю ленту 🐈🐈🐈",
                "'00111101 00101000'",
                "Кажется, вы достигли конца интернета... ",
                "Вы долистали до самого дна, кажется пора потрогать траву...",
                "Netflix выкупил все права. Извините...",
                "Все фильмы просмотрены. Вы победили кино!"
        };

        Label messageLabel = new Label(messages[new Random().nextInt(messages.length)]);
        messageLabel.getStyleClass().add("funny-message");
        messageLabel.setWrapText(true);
        messageLabel.setPrefWidth(300);
        cardContainer.getChildren().add(messageLabel);
    }
}

package com.cinema.onlinecinema.Controller;

import Model.LightweightMovie;
import Model.Movie;
import Model.WatchStatus;
import com.cinema.onlinecinema.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

public class BookmarksPageController {
    @FXML
    public Label emptyLabel;
    @FXML
    private FlowPane cardContainer;

    private WatchStatus currentStatus = WatchStatus.PLAN_TO_WATCH;

    @FXML
    public void initialize() {
        loadMoviesForStatus(currentStatus);
    }

    private void loadMoviesForStatus(WatchStatus status) {
        if(Client.userData == null) return;

        this.currentStatus = status;

        String login = Client.userData.getLogin();
        String url = Client.serverUrl + "api/get-watchlist?login=" + login + "&status=" + status.name();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        Client.sendRequestAsync(request, new TypeReference<List<LightweightMovie>>() {}, movies -> {
            Platform.runLater(() -> {
                cardContainer.getChildren().clear();

                if (movies == null || movies.isEmpty()) {
                    emptyLabel.setVisible(true);
                } else {
                    cardContainer.setVisible(true);
                    emptyLabel.setVisible(false);
                    for (Movie movie : movies) {
                        MovieCardFactory.addMovieCard((LightweightMovie) movie, cardContainer);
                    }
                }
            });
        });
    }

    // Методы для кнопок
    @FXML private void loadPlanToWatch()   { loadMoviesForStatus(WatchStatus.PLAN_TO_WATCH); }
    @FXML private void loadWatching()      { loadMoviesForStatus(WatchStatus.WATCHING); }
    @FXML private void loadDropped()       { loadMoviesForStatus(WatchStatus.DROPPED); }
    @FXML private void loadCompleted()     { loadMoviesForStatus(WatchStatus.COMPLETED); }
}

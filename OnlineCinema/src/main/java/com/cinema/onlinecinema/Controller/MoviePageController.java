package com.cinema.onlinecinema.Controller;

import Model.FullMovie;
import Model.WatchStatus;
import Model.WatchStatusEntry;
import com.cinema.onlinecinema.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;

public class MoviePageController {
    @FXML
    public ImageView posterImageView;
    @FXML
    public Label titleLabel;
    @FXML
    public Label ratingAgeLabel;
    @FXML
    public Label scoreLabel;
    @FXML
    public Label votesLavel;
    @FXML
    public Label episodesLabel;
    @FXML
    public Label studioLabel;
    @FXML
    public Label authorLabel;
    @FXML
    public Label countryAndDate;
    @FXML
    public Label releaseSeries;
    @FXML
    public Label mediaDescription;
    @FXML
    private long movieId;
    @FXML
    private Button statusButton;
    public void setMovieId(long id) {
        this.movieId = id;
        loadFullMovieData();
    }
    private void loadFullMovieData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Client.serverUrl +"api/mediafull/" + movieId))
                .header("Accept", "application/json")
                .GET()
                .build();

        Client.sendRequestAsync(request,
                new TypeReference<FullMovie>(){},
                fullMovie -> {
                    //StartApplication.getMainLayoutController().setContent("com/cinema/onlinecinema/MoviePage/MoviePage.fxml");
                    //тут будем загружать другой fxml файл
                    //showFullMovieDetails(fullMovie)
                    updateUI(fullMovie);
                });
    }

    private void updateUI(FullMovie movie) {
        try {
            if (movie.imageUrl != null && !movie.imageUrl.isEmpty()) throw new Exception("Url not found");
            Image image = new Image(movie.imageUrl);
            posterImageView.setImage(image);
        } catch (Exception e) {
            Image placeholder = new Image(getClass().getResourceAsStream("/images/test_2.jpg"));
            posterImageView.setImage(placeholder);
        }

        titleLabel.setText(movie.title);
        ratingAgeLabel.setText("16+");
        mediaDescription.setText(movie.description);
        countryAndDate.setText(movie.country + ", дата выхода");
        episodesLabel.setText(movie.episodes + " эп., " + "по ~"+movie.avg_duration+" мин.");
        releaseSeries.setText("Сериал выходит каждое воскресенье");
        studioLabel.setText("Студия: " + movie.studio);
        authorLabel.setText("Автор: " + movie.author);

        scoreLabel.setText(movie.userRatingAvg + " * ");
        votesLavel.setText(movie.userRatingCount + " голоса");


        statusButton.setOnAction(e -> openWatchStatusDialog());

        setOnButtonStatus();
    }

    private void openWatchStatusDialog() {
        try {
            if(Client.userData == null) return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cinema/onlinecinema/MoviePage/WatchStatusDialog.fxml"));
            Parent root = loader.load();

            WatchStatusDialogController controller = loader.getController();
            Stage dialog = new Stage();
            controller.setDialogStage(dialog);
            controller.setInitialStatus(statusButton.getText());

            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.setTitle("Статус просмотра");
            dialog.initOwner(statusButton.getScene().getWindow());
            dialog.setResizable(false);
            dialog.showAndWait();

            String result = controller.getSelectedStatus();
            if (result != null) {
                statusButton.setText(result);
                WatchStatus status = WatchStatus.fromLabel(result);

                WatchStatusEntry entry = new WatchStatusEntry(
                        Client.userData.getLogin(),
                        movieId, // ← здесь подставь актуальный ID фильма
                        status,
                        null // оценка, если нужна, можешь расширить
                );
                String json = Client.objectToJson(entry);
                System.out.println("Generated JSON: " + json);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(Client.serverUrl +"api/set-watch-status"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(Client.objectToJson(entry)))
                        .build();

                // Отправляем и обрабатываем результат
                Client.sendRequestAsync(request,
                        new TypeReference<Boolean>() {},
                        success -> {
                            if (success != null && success) {
                                System.out.println("Статус просмотра обновлён успешно");
                            } else {
                                System.out.println("Ошибка при обновлении статуса");
                            }
                        });
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setOnButtonStatus(){
        if(Client.userData == null) return;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Client.serverUrl + "api/get-watch-status?login=" + Client.userData.getLogin()
                        + "&mediaId=" + movieId))
                .header("Accept", "application/json")
                .GET()
                .build();
        System.out.println(request.uri());
        Client.sendRequestAsync(request,
                new TypeReference<WatchStatusEntry>() {},
                entry -> {
            System.out.println(entry);
                    if (entry != null) {
                        String display;
                        switch (entry.status) {
                            case PLAN_TO_WATCH:
                                display = "Буду смотреть";
                                break;
                            case WATCHING:
                                display = "Смотрю";
                                break;
                            case DROPPED:
                                display = "Брошено";
                                break;
                            case COMPLETED:
                                display = "Просмотрено";
                                break;
                            case NOT_WATCHING:
                                display = "Не смотрю";
                                break;
                            default:
                                display = "Неизвестный статус";
                        }
                        statusButton.setText(display);
                    } else {
                        statusButton.setText("Не смотрю");
                    }
                });
    }
}

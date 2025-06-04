package com.cinema.onlinecinema.Controller;

import Model.LightweightMovie;
import com.cinema.onlinecinema.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MovieCardFactory {

    public static void addMovieCard(LightweightMovie movie, FlowPane cardContainer) {
        HBox movieCard = new HBox();
        movieCard.getStyleClass().add("movie-card");
        movieCard.setMaxWidth(305);

        // Добавляем обработчик клика
        movieCard.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Одинарный клик
                loadFullMovieDetails(movie.id);
            }
        });

        ImageView poster = new ImageView();
        try {
            if (movie.imageUrl != null && !movie.imageUrl.isEmpty()) throw new Exception("Url not found");
            Image image = new Image(movie.imageUrl);
            poster.setImage(image);
        } catch (Exception e) {
            Image placeholder = new Image(MovieCardFactory.class.getResourceAsStream("/images/test_2.jpg"));
            poster.setImage(placeholder);
        }


        poster.setFitWidth(100);
        poster.setFitHeight(150);
        poster.setPreserveRatio(true);


        VBox content  = new VBox(5);


        Label titleLabel = new Label(movie.title);
        titleLabel.getStyleClass().add("movie-card-title");


        VBox meta = new VBox();
        meta.getStyleClass().add("movie-card-meta");
        meta.getChildren().addAll(
                new Label("Серий: " + movie.episodes),
                new Label("Оценка: " + movie.userRatingAvg)
        );
        Label descLabel = new Label(movie.description.substring(0,Math.min(movie.description.length(),50)) +"...");
        descLabel.getStyleClass().add("movie-card-description");
        descLabel.setWrapText(true);

        content.getChildren().addAll(titleLabel, meta, descLabel);
        movieCard.getChildren().addAll(poster, content);

        cardContainer.getChildren().add(movieCard);
    }

    private static void loadFullMovieDetails(long movieId) {
        try{
            FXMLLoader loader = new FXMLLoader(
                    MovieCardFactory.class.getResource("/com/cinema/onlinecinema/MoviePage/MoviePage.fxml"));
            Parent moviePage = loader.load();
            MoviePageController controller = loader.getController();
            controller.setMovieId(movieId);

            StartApplication.getMainLayoutController().setContent(moviePage);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Не удалось открыть страницу фильма");
        }


        //StartApplication.getMainLayoutController().setContent("com/cinema/onlinecinema/MoviePage/MoviePage.fxml");
    }
}

package com.cinema.onlinecinema.Controller;

import Model.FullMovie;
import Model.Profile;
import com.cinema.onlinecinema.AuthHelper;
import com.cinema.onlinecinema.Client;
import com.cinema.onlinecinema.StartApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;

import java.net.URI;
import java.net.http.HttpRequest;

public class ProfilePageController {
    @FXML
    private ImageView avatarImageView;
    @FXML
    private Button logoutButton;
    @FXML
    private Label usernameLabel, bioLabel, statusLabel;

    @FXML
    private Label commentsCountLabel, friendsCountLabel;

    @FXML
    private Label watchedCountLabel, plannedCountLabel, droppedCountLabel, averageWatchTimeLabel;

    @FXML
    public void initialize() {
        try{
            logoutButton.setOnAction(e -> {
                // Отчищаем сохраненые данные
                AuthHelper.clearCredentials();
                Client.userData = null;
                Client.profileData = null;

                StartApplication.getMainLayoutController().setContent("Profile/ProfileRegistrationPage.fxml");
            });
            //Подгружаем данные в профиль

            if(Client.userData != null && Client.profileData != null){
                // Просто открываем страницу с профилем и подгружаем данные
                // Также можем отправить повторный запрос, чтобы обновить данные
                SetDataInProfile();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }


    }

    private void SetDataInProfile(){
        Profile data = Client.profileData;
        // ===== Аватарка =====
        Circle clip = new Circle(
                avatarImageView.getFitWidth() / 2,
                avatarImageView.getFitHeight() / 2,
                Math.min(avatarImageView.getFitWidth(), avatarImageView.getFitHeight()) / 2
        );
        avatarImageView.setClip(clip);
        if(data.getAvatarUrl() != null && !data.getAvatarUrl().isEmpty())
            avatarImageView.setImage(new Image(data.getAvatarUrl()));
        avatarImageView.setImage(new Image(getClass().getResourceAsStream("/images/avatar-cat.jpg")));

        // ===== Ник =====
        usernameLabel.setText(data.getNickname());

        // ===== Био =====
        bioLabel.setText(data.getBio());

        // ===== Статус =====
        statusLabel.setText("Онлайн");

        // ===== Статистика =====
        commentsCountLabel.setText(""+data.getComments());
        friendsCountLabel.setText("Их нет");

        // ===== Статистика =====
        watchedCountLabel.setText("0");
        plannedCountLabel.setText("0");
        droppedCountLabel.setText("0");
        averageWatchTimeLabel.setText("0");
    }
}

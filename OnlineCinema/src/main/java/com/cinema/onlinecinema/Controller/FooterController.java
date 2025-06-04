package com.cinema.onlinecinema.Controller;

import Model.Profile;
import Model.User;
import com.cinema.onlinecinema.AuthHelper;
import com.cinema.onlinecinema.Client;
import com.cinema.onlinecinema.StartApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;

import java.net.URI;
import java.net.http.HttpRequest;

public class FooterController {
    @FXML
    private void openProfile() {
        try{
            if(Client.userData != null && Client.profileData != null){
                // Переходим сразу на страницу профиля, тк данные уже есть
                StartApplication.getMainLayoutController().setContent("Profile/ProfilePage.fxml");
                return;
            }

            // Проверяем, наличие сохраненных данны
            String[] userDateToAuth = AuthHelper.loadCredentials();
            if(userDateToAuth != null) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(Client.serverUrl +"api/get-profile" +
                                "?login=" + userDateToAuth[0] +
                                "&password=" + userDateToAuth[1]))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                Client.sendRequestAsync(request,
                        new TypeReference<Profile>() {
                        },
                        profile -> {
                            if(profile != null) {
                                Client.profileData = profile;
                                Client.userData = new User(userDateToAuth[0],userDateToAuth[0],userDateToAuth[1]);
                                StartApplication.getMainLayoutController().setContent("Profile/ProfilePage.fxml");
                            }
                        });
            }
            else StartApplication.getMainLayoutController().setContent("Profile/ProfileRegistrationPage.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    @FXML
    private void openMain() {
        StartApplication.getMainLayoutController().setContent("MainPage/index.fxml");
    }
    @FXML
    private void openBookmarks() {
        StartApplication.getMainLayoutController().setContent("BookmarksPage/BookmarksPage.fxml");
    }
}

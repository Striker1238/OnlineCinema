package com.cinema.onlinecinema.Controller;

import Model.User;
import com.cinema.onlinecinema.AuthHelper;
import com.cinema.onlinecinema.Client;
import com.cinema.onlinecinema.StartApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.Profile;

import java.net.URI;
import java.net.http.HttpRequest;

public class ProfileLoginPageController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private Button togglePasswordButton;
    @FXML
    private CheckBox rememberMeCheckBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label toRegisterLabel;

    private boolean passwordVisible = false;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> tryLogin());

        togglePasswordButton.setOnAction(e -> togglePasswordVisibility());

        toRegisterLabel.setOnMouseClicked(e -> StartApplication.getMainLayoutController().setContent("Profile/ProfileRegistrationPage.fxml"));
        toRegisterLabel.setOnMouseEntered(e -> toRegisterLabel.setStyle("-fx-underline: true; -fx-cursor: hand;"));
        toRegisterLabel.setOnMouseExited(e -> toRegisterLabel.setStyle("-fx-underline: false;"));

        String[] saved = AuthHelper.loadCredentials();
        if (saved != null) {
            loginField.setText(saved[0]);
            passwordField.setText(saved[1]);
            visiblePasswordField.setText(saved[1]);
            rememberMeCheckBox.setSelected(true);
        }

        // Синхронизация видимых полей
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        passwordField.setVisible(!passwordVisible);
        passwordField.setManaged(!passwordVisible);

        visiblePasswordField.setVisible(passwordVisible);
        visiblePasswordField.setManaged(passwordVisible);
    }

    private void tryLogin() {
        String login = loginField.getText().trim();
        String password = passwordVisible ? visiblePasswordField.getText() : passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            System.out.println("Введите логин и пароль.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Client.serverUrl +"api/get-profile?login=" + login + "&password=" + password))
                .header("Accept", "application/json")
                .GET()
                .build();

        Client.sendRequestAsync(request, new TypeReference<Profile>() {}, profile -> {
            if (profile != null) {
                Client.profileData = profile;
                Client.userData = new User(login,login,password);

                if (rememberMeCheckBox.isSelected()) {
                    AuthHelper.saveCredentials(login, password);
                } else {
                    AuthHelper.clearCredentials();
                }

                StartApplication.getMainLayoutController().setContent("Profile/ProfilePage.fxml");
            } else {
                System.out.println("Неверный логин или пароль.");
            }
        });
    }
}

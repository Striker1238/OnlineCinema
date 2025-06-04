package com.cinema.onlinecinema.Controller;

import Model.Profile;
import Model.User;
import com.cinema.onlinecinema.AuthHelper;
import com.cinema.onlinecinema.Client;
import com.cinema.onlinecinema.StartApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

import java.net.URI;
import java.net.http.HttpRequest;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class ProfileRegistrationPageController {
    @FXML
    public TextField firstNameTextField,lastNameTextField;
    @FXML
    public TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField, loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker dateBirthDatePicker;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label isHaveAccountLabel;

    @FXML
    public void initialize() {
        isHaveAccountLabel.setOnMouseClicked(e -> {
            StartApplication.getMainLayoutController().setContent("Profile/ProfileLoginPage.fxml");
        });

        // Смена курсора при наведении
        isHaveAccountLabel.setOnMouseEntered(e -> isHaveAccountLabel.setStyle("-fx-underline: true; -fx-cursor: hand;"));
        isHaveAccountLabel.setOnMouseExited(e -> isHaveAccountLabel.setStyle("-fx-underline: false;"));
    }

    public void startRegistration(){
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String phoneNumber= phoneNumberTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String login = loginTextField.getText().trim();
        String password = passwordField.getText();
        LocalDate dateOfBirth = dateBirthDatePicker.getValue();

        if (email.isEmpty() || login.isEmpty() || password.isEmpty() ||
        firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || dateOfBirth == null) {
            System.out.println("Пожалуйста, заполните все поля.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Введите корректный email.");
            return;
        }
        if(!phoneNumber.matches("^((\\+7|8)[\\s\\-]?)?(\\(?\\d{3}\\)?[\\s\\-]?)?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$")){
            System.out.println("Введен не корректный номер телефона");
            return;
        }

        if (password.length() < 6) {
            System.out.println("Пароль должен содержать не менее 6 символов.");
            return;
        }

        User user = new User(login,email,password,
                firstName,lastName,phoneNumber,
                Date.from(dateBirthDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Client.serverUrl +"api/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(Client.objectToJson(user)))
                .build();

        Client.sendRequestAsync(request,
                new TypeReference<Boolean>() {},
                profile -> {
                    if (profile != null && profile) {
                        Client.userData = user;
                        Client.profileData = new Profile();
                        AuthHelper.saveCredentials(login, password);

                        StartApplication.getMainLayoutController().setContent("Profile/ProfilePage.fxml");
                    }
                    else System.out.println("Пользователь существует!");
        });
    }
}

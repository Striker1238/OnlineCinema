package com.cinema.onlinecinema;

import com.cinema.onlinecinema.Controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static MainLayoutController mainLayoutController;

    public static MainLayoutController getMainLayoutController() {
        return mainLayoutController;
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainLayout.fxml"));
        Scene scene = new Scene(loader.load(),335, 600);
        Client.StartClient();
        mainLayoutController = loader.getController();

        //stage.setResizable(false);
        stage.setTitle("Online Cinema!");
        stage.setScene(scene);
        stage.show();

        // Подгружаем если есть интернет соединение и сервер отвечает
        if(ConnectionChecker.isServerAvailable(Client.serverUrl + "api/ping"))
            mainLayoutController.setContent("MainPage/index.fxml");
        else mainLayoutController.setContent("NoConnectionPage/NoConnectionPage.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}

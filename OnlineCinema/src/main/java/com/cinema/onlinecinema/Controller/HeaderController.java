package com.cinema.onlinecinema.Controller;

import com.cinema.onlinecinema.StartApplication;
import javafx.fxml.FXML;

public class HeaderController {
    @FXML
    private void openSetting() {
        StartApplication.getMainLayoutController().setContent("SettingPage/SettingPage.fxml");
    }

}

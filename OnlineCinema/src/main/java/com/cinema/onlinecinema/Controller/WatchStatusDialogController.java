package com.cinema.onlinecinema.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class WatchStatusDialogController {

    @FXML public RadioButton watchingRadio;
    @FXML public RadioButton completedRadio;
    @FXML private RadioButton notWatchingRadio;
    @FXML private RadioButton planningRadio;
    @FXML private RadioButton droppedRadio;
    private final ToggleGroup toggleGroup = new ToggleGroup(); // создаём вручную


    private String selectedStatus;
    private Stage dialogStage;

    @FXML
    public void initialize() {
        notWatchingRadio.setToggleGroup(toggleGroup);
        planningRadio.setToggleGroup(toggleGroup);
        droppedRadio.setToggleGroup(toggleGroup);
        watchingRadio.setToggleGroup(toggleGroup);
        completedRadio.setToggleGroup(toggleGroup);
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setInitialStatus(String statusText) {
        if (statusText.equals("Не смотрю")) notWatchingRadio.setSelected(true);
        else if (statusText.equals("Буду смотреть")) planningRadio.setSelected(true);
        else if (statusText.equals("Смотрю")) watchingRadio.setSelected(true);
        else if (statusText.equals("Брошено")) droppedRadio.setSelected(true);
        else if (statusText.equals("Просмотрено")) completedRadio.setSelected(true);
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    @FXML
    private void applyStatus() {
        if (toggleGroup.getSelectedToggle() != null) {
            selectedStatus = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            dialogStage.close();
        }
    }

    @FXML
    private void cancel() {
        selectedStatus = null;
        dialogStage.close();
    }
}

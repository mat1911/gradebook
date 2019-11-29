package com.app.controller;


import com.app.app.ConstValues;
import com.app.view.View;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuController {

    @FXML
    private Button teacherButton;

    @FXML
    private Button groupButton;

    @FXML
    private Button logoutButton;

    private View view = new View();

    @FXML
    protected void openTeacherWindow(){

        Platform.runLater(() -> {
            view.showWindow(ConstValues.TEACHER_FILE_NAME, 600, 400);
        });

    }


}

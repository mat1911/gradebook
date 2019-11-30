package com.app.controller;

import com.app.app.ConstValues;
import com.app.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class TeacherMenuController {

    private ViewManager viewManager = ViewManager.getInstance();

    @FXML
    protected GridPane menuGridPane;
    @FXML
    protected ToolBar menuToolBar;
    @FXML
    protected Button testButton;
    @FXML
    protected Button testButton2;
    @FXML
    protected SubScene menuSubScene;

    @FXML
    protected void testButtonAction(ActionEvent actionEvent) {
        viewManager.setSubScene(menuSubScene);
        viewManager.setSubScene(ConstValues.LOGIN_FILENAME);
    }

    @FXML
    protected void testButton2Action(ActionEvent actionEvent) {
        viewManager.setSubScene(menuSubScene);
        viewManager.setSubScene(ConstValues.TEACHER_TEST2_FILENAME);
    }
}

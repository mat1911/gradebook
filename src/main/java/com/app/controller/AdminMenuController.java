package com.app.controller;

import com.app.utility.SubSceneViewType;
import com.app.view.ViewManager;
import com.app.utility.WindowViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class AdminMenuController {
    private ViewManager viewManager = ViewManager.getInstance();

    @FXML
    private GridPane menuGridPane;
    @FXML
    private ToolBar menuToolBar;
    @FXML
    private Button teacherButton;
    @FXML
    private Button groupButton;
    @FXML
    private Button logoutButton;
    @FXML
    private SubScene menuSubScene;

    @FXML
    private void openTeacherScene(ActionEvent actionEvent) {
        viewManager.setSubScene(menuSubScene);
        viewManager.showSubSceneView(SubSceneViewType.TEACHER_VIEW);
    }

    @FXML
    private void openGroup(ActionEvent actionEvent) {
        //TODO
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
    }
}

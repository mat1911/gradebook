package com.app.controller;

import com.app.utility.SubSceneViewType;
import com.app.utility.WindowViewType;
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
    private GridPane menuGridPane;
    @FXML
    private ToolBar menuToolBar;
    @FXML
    private Button testButton;
    @FXML
    private Button logoutButton;
    @FXML
    private SubScene menuSubScene;

    @FXML
    private void testButtonAction(ActionEvent actionEvent) {
        viewManager.setSubScene(menuSubScene);
        viewManager.showSubSceneView(SubSceneViewType.TEACHER_VIEW);
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
    }
}

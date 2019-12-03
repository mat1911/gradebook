package com.app.controller;

import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import com.app.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class AdminMenuController extends MenuController {
    private ViewManager viewManager = new ViewManager();

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
    private GridPane contentPane;

    @FXML
    private void openTeacherScene(ActionEvent actionEvent) {
        changeSubView(SubViewType.TEACHER_VIEW);
    }

    @FXML
    private void openGroup(ActionEvent actionEvent) {

    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
    }
}

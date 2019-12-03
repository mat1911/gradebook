package com.app.controller.menu;

import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import com.app.controller.ViewManager;
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
    private void openTeacherSubView(ActionEvent actionEvent) {
        changeSubView(SubViewType.TEACHER_VIEW);
    }

    @FXML
    private void openGroupSubView(ActionEvent actionEvent) {

    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
    }
}

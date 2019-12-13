package com.app.controller.menu;

import com.app.controller.ViewManager;
import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class AdminMenuController {
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
        viewManager.changeSubView(SubViewType.A_TEACHER_VIEW, contentPane);
    }

    @FXML
    private void openGroupSubView(ActionEvent actionEvent) {
        viewManager.changeSubView(SubViewType.A_GROUP_VIEW, contentPane);
    }

    @FXML
    private void openSubjectSubView(ActionEvent actionEvent) {
        viewManager.changeSubView(SubViewType.A_SUBJECTS_VIEW, contentPane);
    }

    @FXML
    private void openPlanSubView(ActionEvent actionEvent) {
        viewManager.changeSubView(SubViewType.A_PLAN_VIEW, contentPane);
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
    }
}

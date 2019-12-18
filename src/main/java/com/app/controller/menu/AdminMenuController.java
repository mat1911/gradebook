package com.app.controller.menu;

import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import com.app.utility.BackgroundTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class AdminMenuController extends MenuController {
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
    private void openTeacherSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.A_TEACHER_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void openGroupSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.A_GROUP_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void openSubjectSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.A_SUBJECTS_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void openPlanSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.A_PLAN_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.showView(WindowViewType.LOGIN_VIEW));
        backgroundTask.execute();
    }

    public void initialize() {
        super.initialize();
    }
}

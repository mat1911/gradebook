package com.app.controller.menu;

import com.app.entity.Teacher;
import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import com.app.utility.BackgroundTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class TeacherMenuController extends MenuController {
    @FXML
    private GridPane mainPane;
    @FXML
    private ToolBar menuToolBar;
    @FXML
    private Button timetableButton;
    @FXML
    private Button groupButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Label loggedUser;
    @FXML
    private Label userInfo;

    @FXML
    private void openTimetableSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.T_TIMETABLE_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void openGroupSubView(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> viewManager.changeSubView(SubViewType.T_GROUP_VIEW));
        backgroundTask.execute();
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            viewManager.showView(WindowViewType.LOGIN_VIEW);
            appContext.setLoggedTeacher(null);
        });
        backgroundTask.execute();
    }

    public void initialize() {
        super.initialize();

        Teacher loggedTeacher = appContext.getLoggedTeacher();
        loggedUser.setText("Zalogowano jako: " + loggedTeacher.getName() + " " + loggedTeacher.getSurname());
    }
}

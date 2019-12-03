package com.app.controller.menu;

import com.app.app.AppContext;
import com.app.controller.ViewManager;
import com.app.entity.Teacher;
import com.app.enums.WindowViewType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class TeacherMenuController {
    private ViewManager viewManager = new ViewManager();
    private AppContext appContext = AppContext.getInstance();

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
    private GridPane contentPane;
    @FXML
    private Label userInfo;

    @FXML
    private void openTimetableSubView(ActionEvent actionEvent) {

    }

    @FXML
    private void openGroupSubView(ActionEvent actionEvent) {

    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
        appContext.setLoggedTeacher(null);
    }

    public void initialize() {
        Teacher loggedTeacher = appContext.getLoggedTeacher();
        loggedUser.setText("Zalogowano jako: " + loggedTeacher.getName() + " " + loggedTeacher.getSurname());
    }
}

package com.app.controller;

import com.app.app.AppContext;
import com.app.entity.Teacher;
import com.app.enums.WindowViewType;
import com.app.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

public class TeacherMenuController {
    private ViewManager viewManager = new ViewManager();
    private AppContext appContext = AppContext.getInstance();
    private Teacher loggedTeacher;

    @FXML
    private GridPane mainPane;
    @FXML
    private ToolBar menuToolBar;
    @FXML
    private Button testButton;
    @FXML
    private Button logoutButton;
    @FXML
    private GridPane contentPane;
    @FXML
    private Label userInfo;

    @FXML
    private void testButtonAction(ActionEvent actionEvent) {
        if(loggedTeacher != null)
            System.out.println(loggedTeacher.getEmail());
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        viewManager.showView(WindowViewType.LOGIN_VIEW);
        appContext.setLoggedTeacher(null);

    }

    public void initialize() {
        loggedTeacher = appContext.getLoggedTeacher();
    }
}

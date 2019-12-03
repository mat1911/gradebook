package com.app.controller;

import com.app.app.AppContext;
import com.app.entity.Teacher;
import com.app.enums.LoginType;
import com.app.enums.WindowViewType;
import com.app.exceptions.ObjectNotFoundException;
import com.app.service.LoginService;
import com.app.service.TeacherService;
import com.app.utility.MyTask;
import com.app.view.LoginView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private LoginView loginView = new LoginView();

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label wrongEmail;
    @FXML
    private Label wrongPass;

    @FXML
    private void tryLogin() {
        MyTask myTask = new MyTask(() -> {
            try {
                LoginService loginService = new LoginService();
                LoginType loginType = loginService.tryLogin(emailField.getText(), passField.getText());
                openUserMenu(loginType);
            } catch (ObjectNotFoundException e) {
                Platform.runLater(() -> loginView.showLabel(wrongEmail, true));
            } catch (IllegalArgumentException e) {
                Platform.runLater(() -> loginView.showLabel(wrongPass, true));
            }
        });
        myTask.execute();
    }

    @FXML
    private void emailChanged() {
        loginView.showLabel(wrongEmail, false);
    }

    @FXML
    private void passChanged() {
        loginView.showLabel(wrongPass, false);
    }

    private void openUserMenu(LoginType tryLogin) {
        ViewManager viewManager = new ViewManager();
        switch(tryLogin) {
            case ADMIN:
                viewManager.showView(WindowViewType.ADMIN_MENU_VIEW);
                break;
            case TEACHER:
                TeacherService teacherService = new TeacherService();
                Teacher loggedTeacher = teacherService.findByEmail(emailField.getText());
                AppContext.getInstance().setLoggedTeacher(loggedTeacher);
                viewManager.showView(WindowViewType.TEACHER_MENU_VIEW);
                break;
        }
    }

}

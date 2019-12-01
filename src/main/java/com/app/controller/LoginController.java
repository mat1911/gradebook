package com.app.controller;

import com.app.exception.ObjectNotFoundException;
import com.app.service.LoginService;
import com.app.utility.LoginType;
import com.app.utility.MyTask;
import com.app.view.LoginView;
import com.app.view.ViewManager;
import com.app.utility.WindowViewType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private LoginService loginService = new LoginService();
    private LoginView loginView = new LoginView();
    private ViewManager viewManager = ViewManager.getInstance();

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button loginButton;
    @FXML
    private Label wrongEmail;
    @FXML
    private Label wrongPass;

    @FXML
    private void tryLogin() {
        MyTask myTask = new MyTask(() -> {
            try {
                LoginType loginType = loginService.tryLogin(emailField.getText(), passField.getText());
                Platform.runLater(() -> openMenu(loginType));
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
        MyTask myTask = new MyTask(() -> Platform.runLater(() -> loginView.showLabel(wrongEmail, false)));
        myTask.execute();
    }

    @FXML
    private void passChanged() {
        MyTask myTask = new MyTask(() -> Platform.runLater(() -> loginView.showLabel(wrongPass, false)));
        myTask.execute();
    }

    private void openMenu(LoginType tryLogin) {
        switch(tryLogin) {
            case ADMIN:
                viewManager.showView(WindowViewType.ADMIN_MENU_VIEW);
                break;
            case TEACHER:
                viewManager.showView(WindowViewType.TEACHER_MENU_VIEW);
                break;
        }
    }


}

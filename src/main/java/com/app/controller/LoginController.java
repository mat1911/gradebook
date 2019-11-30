package com.app.controller;

import com.app.exception.ObjectNotFoundException;
import com.app.service.LoginService;
import com.app.service.MyTask;
import com.app.service.LoginType;
import com.app.view.LoginView;
import com.app.view.ViewManager;
import com.app.view.WindowView;
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
    protected void tryLogin() {
        MyTask myTask = new MyTask(() -> {
            try {
                LoginType loginType = loginService.tryLogin(emailField.getText(), passField.getText());
                Platform.runLater(() -> openMenu(loginType));
            } catch (ObjectNotFoundException e) {
                Platform.runLater(() -> loginView.showLabel(wrongEmail));
            } catch (IllegalArgumentException e) {
                Platform.runLater(() -> loginView.showLabel(wrongPass));
            }
        });
        myTask.execute();
    }

    @FXML
    protected void emailChanged() {
        MyTask myTask = new MyTask(() -> Platform.runLater(() -> loginView.hideLabel(wrongEmail)));
        myTask.execute();
    }

    @FXML
    protected void passChanged() {
        MyTask myTask = new MyTask(() -> Platform.runLater(() -> loginView.hideLabel(wrongPass)));
        myTask.execute();
    }

    private void openMenu(LoginType tryLogin) {
        switch(tryLogin) {
            case ADMIN:
                viewManager.showView(WindowView.ADMIN_MENU_VIEW);
                break;
            case TEACHER:
                viewManager.showView(WindowView.TEACHER_MENU_VIEW);
                break;
        }
    }


}

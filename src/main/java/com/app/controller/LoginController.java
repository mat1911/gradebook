package com.app.controller;

import com.app.exception.ObjectNotFoundException;
import com.app.service.LoginService;
import com.app.service.MyTask;
import com.app.view.LoginView;
import com.app.view.ViewManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private LoginService loginService = new LoginService();
    private LoginView loginView = new LoginView();
    private ViewManager viewManager = new ViewManager();

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
                LoginService.LoginType loginType = loginService.tryLogin(emailField.getText(), passField.getText());
                Platform.runLater(() -> openMenu(loginType));
            } catch (ObjectNotFoundException e) {
                Platform.runLater(() -> loginView.showLabel(wrongEmail));
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                Platform.runLater(() -> loginView.showLabel(wrongPass));
                System.out.println(e.getMessage());
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

    private void openMenu(LoginService.LoginType tryLogin) {
        switch(tryLogin) {
            case ADMIN:
                viewManager.showView(ViewManager.WindowView.ADMIN_MENU_VIEW);
                break;
            case TEACHER:
                viewManager.showView(ViewManager.WindowView.TEACHER_MENU_VIEW);
                break;
        }
    }


}

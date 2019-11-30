package com.app.controller;


import com.app.view.ViewManager;
import com.app.view.WindowView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MenuController {

    @FXML
    private Button teacherButton;

    @FXML
    private Button groupButton;

    @FXML
    private Button logoutButton;

    @FXML
    protected void openTeacherWindow(){
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.showView(WindowView.TEACHER_VIEW);
    }


}

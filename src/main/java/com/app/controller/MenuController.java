package com.app.controller;


import com.app.view.ViewManager;
import com.app.utility.WindowViewType;
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
    private void openTeacherWindow(){
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.showView(WindowViewType.TEACHER_VIEW);
    }


}

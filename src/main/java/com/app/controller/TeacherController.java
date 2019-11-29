package com.app.controller;

import com.app.view.TeacherView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class TeacherController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    private TeacherView teacherView = new TeacherView();

    @FXML
    protected void handleTaskAddButton(){

        teacherView.loadAddFields(mainBorderPane);
    }

    @FXML
    protected void setSmallerAddBackgroundImage(){

        setSmallerBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setSmallerRemoveBackgroundImage(){

        setSmallerBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setSmallerEditBackgroundImage(){

        setSmallerBackgroundImage(editButton, "pencil.png");
    }

    @FXML
    protected void setNormalAddBackgroundImage(){

        setNormalBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setNormalRemoveBackgroundImage(){

        setNormalBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setNormalEditBackgroundImage(){

        setNormalBackgroundImage(editButton, "pencil.png");
    }

    private void setSmallerBackgroundImage(Button button, String imageName){

        teacherView.showSmallerBackgroundImage(button, imageName);
    }

    private void setNormalBackgroundImage(Button button, String imageName){

        teacherView.showNormalBackgroundImage(button, imageName);
    }

}

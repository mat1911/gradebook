package com.app.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TeacherView {

    public void loadAddFields(BorderPane mainBorderPane) {

        try {
            ((GridPane)mainBorderPane.getCenter()).getChildren().clear();
            ((GridPane)mainBorderPane.getCenter()).getChildren().add(FXMLLoader.load(getClass().getResource("/addTeacher.fxml")));
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void showSmallerBackgroundImage(Button button, String pictureName){

        String path = "'file:src/main/resources/static/" + pictureName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 15 15; -fx-background-color: transparent;");
    }

    public void showNormalBackgroundImage(Button button, String pictureName){

        String path = "'file:src/main/resources/static/" + pictureName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 20 20; -fx-background-color: transparent;");
    }
}

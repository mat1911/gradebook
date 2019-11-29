package com.app.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View {

    public Stage showWindow(String windowName, int sceneWidth, int sceneHeight){

        Stage stage = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + windowName));
            Parent root = loader.load();
            Scene scene  = new Scene(root, sceneWidth, sceneHeight);

            stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }

        return stage;
    }
}

package com.app.view;

import com.app.app.ConstValues;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManager {

    private Stage showWindow(String windowName, int sceneWidth, int sceneHeight){

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

    public Stage showView(WindowView windowView) {
        switch(windowView) {
            case LOGIN_VIEW:
                return showWindow(ConstValues.LOGIN_FILENAME, ConstValues.LOGIN_WIDTH, ConstValues.LOGIN_HEIGHT);
            case ADMIN_MENU_VIEW:
                return showWindow(ConstValues.ADMIN_MENU_FILENAME, ConstValues.ADMIN_MENU_WIDTH, ConstValues.ADMIN_MENU_HEIGHT);
            case TEACHER_MENU_VIEW:
            case TEACHER_VIEW:
                return showWindow(ConstValues.TEACHER_FILE_NAME, ConstValues.TEACHER_WIDTH, ConstValues.TEACHER_HEIGHT);
        }
        throw new IllegalArgumentException("No such windowView");
    }

    public enum WindowView {
        ADMIN_MENU_VIEW, TEACHER_MENU_VIEW, LOGIN_VIEW, TEACHER_VIEW
    }
}

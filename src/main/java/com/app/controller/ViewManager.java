package com.app.controller;

import com.app.app.AppContext;
import com.app.app.ConstValues;
import com.app.enums.SubViewType;
import com.app.enums.WindowViewType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class ViewManager {
    private AppContext appContext = AppContext.getInstance();

    public void showView(WindowViewType windowViewType) {
        switch(windowViewType) {
            case LOGIN_VIEW:
                showWindow(ConstValues.LOGIN_FILENAME, ConstValues.LOGIN_WIDTH, ConstValues.LOGIN_HEIGHT);
                return;
            case ADMIN_MENU_VIEW:
                showWindow(ConstValues.ADMIN_MENU_FILENAME, ConstValues.ADMIN_MENU_WIDTH, ConstValues.ADMIN_MENU_HEIGHT);
                return;
            case TEACHER_MENU_VIEW:
                showWindow(ConstValues.TEACHER_MENU_FILENAME, ConstValues.TEACHER_MENU_WIDTH, ConstValues.TEACHER_MENU_HEIGHT);
                return;
        }
        throw new IllegalArgumentException("No such windowView!");
    }

    private void showWindow(String windowName, int sceneWidth, int sceneHeight){
        Parent root = getRoot(windowName);
        setScene(root, sceneWidth, sceneHeight);
        Platform.runLater(() -> appContext.getStage().setScene(appContext.getScene()));
    }

    private void setScene(Parent root, int sceneWidth, int sceneHeight) {
        appContext.setScene(new Scene(root, sceneWidth, sceneHeight));
    }

    private Parent getRoot(String windowName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + windowName));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Parent getRoot(SubViewType subViewType) {
        switch(subViewType) {
            case TEACHER_VIEW:
                return getRoot(ConstValues.TEACHER_FILENAME);
        }
        throw new IllegalArgumentException("No such subView!");
    }

    public void showStage() {
        appContext.getStage().show();
    }
}

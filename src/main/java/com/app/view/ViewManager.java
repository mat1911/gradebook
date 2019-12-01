package com.app.view;

import com.app.app.ConstValues;
import com.app.utility.SubSceneViewType;
import com.app.utility.WindowViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class ViewManager {
    private static volatile ViewManager instance;
    private volatile Stage stage = new Stage();
    private volatile Scene scene;
    private volatile SubScene subScene = new SubScene(null, 0, 0);

    private ViewManager() {}

    public static ViewManager getInstance() {
        if(instance == null)
            instance = new ViewManager();
        return instance;
    }

    public void showSubSceneView(SubSceneViewType subSceneViewType) {
        switch(subSceneViewType) {
            case TEACHER_VIEW:
                showSubScene(ConstValues.TEACHER_FILE_NAME);
                return;
        }
        throw new IllegalArgumentException("No such subSceneView!");
    }

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
        stage.setScene(scene);
    }

    private void showSubScene(String windowName) {
        Parent root = getRoot(windowName);
        subScene.setRoot(root);
    }
    private void setScene(Parent root, int sceneWidth, int sceneHeight) {
        scene = new Scene(root, sceneWidth, sceneHeight);
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

    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
    }
}

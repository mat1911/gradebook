package com.app.app;

import com.app.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /* Potem do zmiany pewnie, narazie daje inny start zeby sie odpalalo logowanie na wejscie

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
        Parent root = loader.load();

        Scene mainScene = new Scene(root, 600, 400);
        stage.setScene(mainScene);
        stage.show();

    }*/

    @Override
    public void start(Stage stage) throws Exception {
        View view = new View();
        view.showWindow(ConstValues.LOGIN_FILENAME, ConstValues.LOGIN_WIDTH, ConstValues.LOGIN_HEIGHT);
    }
}

package com.app.app;

import com.app.controller.ViewManager;
import com.app.enums.WindowViewType;
import com.app.repository.connection.DbConnection;
import com.app.utility.BackgroundTask;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ViewManager viewManager = new ViewManager();
        AppContext.getInstance().setStage(stage);
        viewManager.showView(WindowViewType.LOGIN_VIEW);
        viewManager.showStage();

        BackgroundTask backgroundTask = new BackgroundTask(DbConnection::load);
        backgroundTask.execute();
    }
}

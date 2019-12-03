package com.app.app;

import com.app.controller.ViewManager;
import com.app.utility.DBTest;
import com.app.utility.MyTask;
import com.app.enums.WindowViewType;
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

        MyTask myTask = new MyTask(() -> DBTest.populateDB());
        myTask.execute();
    }
}

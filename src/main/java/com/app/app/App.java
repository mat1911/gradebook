package com.app.app;

import com.app.service.MyTask;
import com.app.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager viewManager = new ViewManager();
        viewManager.showView(ViewManager.WindowView.LOGIN_VIEW);

        MyTask myTask = new MyTask(() -> DBTest.populateDB());
        myTask.execute();
    }
}

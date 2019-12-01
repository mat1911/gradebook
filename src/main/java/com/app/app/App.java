package com.app.app;

import com.app.utility.MyTask;
import com.app.view.ViewManager;
import com.app.utility.WindowViewType;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setStage(stage);
        viewManager.showView(WindowViewType.LOGIN_VIEW);
        stage.show();

        MyTask myTask = new MyTask(() -> DBTest.populateDB());
        myTask.execute();
    }
}

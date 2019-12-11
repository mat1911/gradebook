package com.app.app;

import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class AppContext {
    private static AppContext instance = new AppContext();
    private Stage stage;
    private Scene scene;

    private Teacher loggedTeacher;
    private StudentGroup studentGroup;

    private AppContext() {}

    public static AppContext getInstance() {
        return instance;
    }
}
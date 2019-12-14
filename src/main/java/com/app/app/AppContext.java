package com.app.app;

import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class AppContext {
    private static AppContext instance = new AppContext();
    private Stage stage;
    private Scene scene;
    private Pane contentPane;

    private Teacher loggedTeacher;
    private StudentGroup studentGroup;
    private Student selectedStudent;

    private AppContext() {}

    public static AppContext getInstance() {
        return instance;
    }
}

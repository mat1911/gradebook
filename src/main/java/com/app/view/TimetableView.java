package com.app.view;

import com.app.entity.Lesson;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class TimetableView implements CrudView<Lesson> {
    public void setLabelText(Label label, String text) {
        label.setText(text);
    }

    public void setPropertyValueFactories(
            TableColumn<Lesson, String> subjectCol,
            TableColumn<Lesson, String> groupCol) {

        subjectCol.setCellValueFactory(lesson -> new SimpleStringProperty(lesson.getValue().getSubject().getName()));
        groupCol.setCellValueFactory(lesson -> new SimpleStringProperty(lesson.getValue().getGroup().getName()));
    }
}

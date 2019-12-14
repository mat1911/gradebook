package com.app.view;

import com.app.entity.Mark;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class MarkView implements CrudView<Mark> {

    public void setPropertyValueFactories(TableColumn<Mark, String> teacherCol) {
        teacherCol.setCellValueFactory(mark ->
            new SimpleStringProperty(
                    mark.getValue().getTeacher().getName() + " " + mark.getValue().getTeacher().getSurname())
        );
    }
}

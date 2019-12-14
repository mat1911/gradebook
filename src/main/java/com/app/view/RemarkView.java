package com.app.view;

import com.app.entity.Remark;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class RemarkView implements CrudView<Remark> {
    public void setPropertyValueFactories(TableColumn<Remark, String> remarkDateCol, TableColumn<Remark, String> remarkTeacherCol) {
        remarkDateCol.setCellValueFactory(remark ->
                new SimpleStringProperty(
                        remark.getValue().getLesson().getDate().toString())
        );

        remarkTeacherCol.setCellValueFactory(remark ->
                new SimpleStringProperty(
                        remark.getValue().getTeacher().getName() + " " + remark.getValue().getTeacher().getSurname())
        );
    }
}

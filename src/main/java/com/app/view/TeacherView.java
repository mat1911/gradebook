package com.app.view;

import com.app.entity.Teacher;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class TeacherView implements CrudView<Teacher>{

    @Override
    public void editObjectInTable(TableView<Teacher> table, Teacher teacher) {

        Teacher t = table.getItems()
                .filtered(tr -> tr.getId() == teacher.getId())
                .get(0);

        t.setName(teacher.getName());
        t.setSurname(teacher.getSurname());
        t.setEmail(teacher.getEmail());

        table.refresh();
    }

    public void changeButtonsAccessibility(Button[] toDisableButtons, Button[] toEnableButtons){

        Arrays.stream(toDisableButtons).forEach(button -> button.setDisable(true));
        Arrays.stream(toEnableButtons).forEach(button -> button.setDisable(false));
    }

    public void changeFieldsAccessibility(TextField[] toDisableFields, TextField[] toEnableFields){

        Arrays.stream(toDisableFields).forEach(field -> field.setDisable(true));
        Arrays.stream(toEnableFields).forEach(field -> field.setDisable(false));
    }

}

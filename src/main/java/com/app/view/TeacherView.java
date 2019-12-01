package com.app.view;

import com.app.entity.Teacher;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class TeacherView {

    public void addTeacherToTable(TableView<Teacher> table, Teacher teacher){
        table.getItems().add(teacher);
        table.refresh();
    }

    public void editTeacherToTable(TableView<Teacher> table, Teacher teacher) {

        Teacher t = table.getItems()
                .filtered(tr -> tr.getId() == teacher.getId())
                .get(0);

       // table.getItems().remove(t);

        t.setName(teacher.getName());
        t.setSurname(teacher.getSurname());
        t.setEmail(teacher.getEmail());
       // table.getItems().add(t);

        table.refresh();
    }

    public void removeTeacherFromTable(TableView<Teacher> table, Teacher teacher){
        table.getItems().remove(teacher);
        table.refresh();
    }

    public void showErrorMessage(Label errorLabel){

        errorLabel.setText("Sprawdź poprawność uzupełnionych pół!");
        errorLabel.setVisible(true);
    }

    public void hideErrorMessage(Label errorLabel){

        errorLabel.setText("Sprawdź poprawność uzupełnionych pół!");
        errorLabel.setVisible(false);
    }

    public void changeButtonsAccessibility(Button[] toDisableButtons, Button[] toEnableButtons){

        Arrays.stream(toDisableButtons).forEach(button -> button.setDisable(true));
        Arrays.stream(toEnableButtons).forEach(button -> button.setDisable(false));
    }

    public void changeFieldsAccessibility(TextField[] toDisableFields, TextField[] toEnableFields){

        Arrays.stream(toDisableFields).forEach(field -> field.setDisable(true));
        Arrays.stream(toEnableFields).forEach(field -> field.setDisable(false));
    }

    public void showSmallerBackgroundImage(Button button, String pictureName){
        String path = "'file:src/main/resources/static/" + pictureName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 15 15; -fx-background-color: transparent;");
    }

    public void showNormalBackgroundImage(Button button, String pictureName){
        String path = "'file:src/main/resources/static/" + pictureName + "'";
        button.setStyle("-fx-background-image: url(" + path + "); -fx-background-repeat: stretch; -fx-background-position: center center; -fx-background-size: 20 20; -fx-background-color: transparent;");
    }
}

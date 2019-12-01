package com.app.controller;

import com.app.entity.Teacher;
import com.app.service.ActionPerformer;
import com.app.service.MyTask;
import com.app.service.TeacherService;
import com.app.view.TeacherView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class TeacherController {

    @FXML
    private Label errorMessage;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button editButton;

    @FXML
    private Button addOkButton;

    @FXML
    private Button removeOkButton;

    @FXML
    private Button editOkButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Teacher> teachersTable;

    private TeacherView teacherView = new TeacherView();

    private TeacherService teacherService = new TeacherService();

    private ObservableList<Teacher> allTeachers;

    public TeacherController() {


/*
        this.allTeachers = FXCollections.observableArrayList(
                Teacher.builder().id(1L).name("Basia").surname("Kowal").email("kowal@gmail.com").build(),
                Teacher.builder().id(2L).name("Waldemar").surname("Pawlak").email("pawlak123@onet.pl").build(),
                Teacher.builder().id(3L).name("Zbigniew").surname("Kowal").email("zbigniew.kowal123@gmail.com").build(),
                Teacher.builder().id(4L).name("Milosz").surname("Czeslaw").email("czesio39@wp.pl").build()
        );

        this.allTeachers.forEach(tr -> teacherService.addTeacherToDatabase(tr.getName(), tr.getSurname(), tr.getEmail()));
*/

        this.allTeachers = FXCollections.observableList(teacherService.getAllTeachers());

        Platform.runLater(() -> teacherView.setTeachersInTable(teachersTable, allTeachers));
    }

    @FXML
    protected void addNewTeacher(){

        performAction(() -> {

            Teacher addedTeacher = teacherService
                    .addTeacherToDatabase(nameField.getText(), surnameField.getText(), emailField.getText());

            allTeachers.add(addedTeacher);

            Platform.runLater(() -> teacherView.addTeacherToTable(teachersTable, addedTeacher));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void removeTeacher(){

        performAction(() -> {

            Teacher removedTeacher = teacherService
                    .removeTeacherFromDatabase(idField.getText());

            allTeachers.remove(removedTeacher);

            Platform.runLater(() -> teacherView.removeTeacherFromTable(teachersTable, removedTeacher));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void editTeacher(){

        performAction(() -> {

            Teacher teacher = teachersTable.getItems()
                    .filtered(tr -> tr.getId() == Long.parseLong(idField.getText()))
                    .get(0);

            Teacher editedTeacher = teacherService
                    .editTeacher(teacher, nameField.getText(), surnameField.getText(), emailField.getText());

            allTeachers.remove(teacher);
            allTeachers.add(editedTeacher);

            Platform.runLater(() -> teacherView.editTeacherToTable(teachersTable, editedTeacher));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void searchTeacher(){

        performAction(() -> {

            FilteredList<Teacher> filteredTeachers = teacherService
                    .filterTeachers(allTeachers, searchField.getText());

            Platform.runLater(() -> teacherView.setTeachersInTable(teachersTable, filteredTeachers));

        }, "");

    }

    @FXML
    protected void changeAccessibilityForAddingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, removeOkButton}, new Button[]{addOkButton});
        changeAccessibilityForFields(new TextField[]{idField}, new TextField[]{nameField, surnameField, emailField});
    }

    @FXML
    protected void changeAccessibilityForRemovingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, addOkButton}, new Button[]{removeOkButton});
        changeAccessibilityForFields(new TextField[]{nameField, surnameField, emailField}, new TextField[]{idField});
    }

    @FXML
    protected void changeAccessibilityForEditingNodes(){

        changeAccessibilityForButtons(new Button[]{removeOkButton, addOkButton}, new Button[]{editOkButton});
        changeAccessibilityForFields(new TextField[]{}, new TextField[]{idField, nameField, surnameField, emailField});
    }


    @FXML
    protected void setSmallerAddBackgroundImage(){

        setSmallerBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setSmallerRemoveBackgroundImage(){

        setSmallerBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setSmallerEditBackgroundImage(){

        setSmallerBackgroundImage(editButton, "pencil.png");
    }

    @FXML
    protected void setNormalAddBackgroundImage(){

        setNormalBackgroundImage(addButton, "plus.png");
    }

    @FXML
    protected void setNormalRemoveBackgroundImage(){

        setNormalBackgroundImage(removeButton, "minus.png");
    }

    @FXML
    protected void setNormalEditBackgroundImage(){

        setNormalBackgroundImage(editButton, "pencil.png");
    }

    private void performAction(ActionPerformer actionPerformer, String errorMess){

        MyTask myTask = new MyTask(() -> {
            Platform.runLater(() -> teacherView.hideErrorMessage(errorMessage));
            try {
                actionPerformer.perform();

            } catch (Exception e) {
                Platform.runLater(() -> teacherView.showErrorMessage(errorMessage, errorMess));
                System.out.println(e.getMessage());
            }
        });
        myTask.execute();
    }

    private void changeAccessibilityForButtons(Button[] toDisableButtons, Button[] toEnableButtons){
        teacherView.changeButtonsAccessibility(toDisableButtons, toEnableButtons);
    }

    private void changeAccessibilityForFields(TextField[] toDisableFields, TextField[] toEnableFields){
        teacherView.changeFieldsAccessibility(toDisableFields, toEnableFields);
    }

    private void setSmallerBackgroundImage(Button button, String imageName){

        teacherView.showSmallerBackgroundImage(button, imageName);
    }

    private void setNormalBackgroundImage(Button button, String imageName){

        teacherView.showNormalBackgroundImage(button, imageName);
    }

}

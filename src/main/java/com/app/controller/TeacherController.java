package com.app.controller;

import com.app.entity.Teacher;
import com.app.service.TeacherService;
import com.app.view.TeacherView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class TeacherController extends CrudController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Teacher> teachersTable;

    private TeacherView teacherView = new TeacherView();

    private TeacherService teacherService = new TeacherService();

    private ObservableList<Teacher> allTeachers;

    public TeacherController() {

        this.allTeachers = FXCollections.observableArrayList(
                Teacher.builder().id(1L).name("Basia").surname("Kowal").email("kowal@gmail.com").build(),
                Teacher.builder().id(2L).name("Waldemar").surname("Pawlak").email("pawlak123@onet.pl").build(),
                Teacher.builder().id(3L).name("Zbigniew").surname("Kowal").email("zbigniew.kowal123@gmail.com").build(),
                Teacher.builder().id(4L).name("Milosz").surname("Czeslaw").email("czesio39@wp.pl").build()
        );

        this.allTeachers.forEach(tr -> teacherService.addObjectToDatabase(FXCollections.observableArrayList(tr.getName(), tr.getSurname(), tr.getEmail())));

        this.allTeachers = FXCollections.observableList(teacherService.getAllTeachers());

        FilteredList<Teacher> filtered = new FilteredList(allTeachers);

        Platform.runLater(() -> {
            teacherView.setObjectsInTable(teachersTable, filtered);
            initFields(teachersTable, allTeachers, teacherService, teacherView);
        });


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

    private void changeAccessibilityForButtons(Button[] toDisableButtons, Button[] toEnableButtons){
        teacherView.changeButtonsAccessibility(toDisableButtons, toEnableButtons);
    }

    private void changeAccessibilityForFields(TextField[] toDisableFields, TextField[] toEnableFields){
        teacherView.changeFieldsAccessibility(toDisableFields, toEnableFields);
    }



}

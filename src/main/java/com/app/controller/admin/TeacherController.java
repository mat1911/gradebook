package com.app.controller.admin;

import com.app.entity.Teacher;
import com.app.service.TeacherService;
import com.app.utility.MyTask;
import com.app.view.TeacherView;
import javafx.application.Platform;
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
    private TableView<Teacher> teachersTable;

    private TeacherView teacherView = new TeacherView();
    private TeacherService teacherService = new TeacherService();

    @FXML
    protected void addNewTeacher(){
        MyTask myTask = new MyTask(() -> {
            Platform.runLater(() -> teacherView.hideErrorMessage(errorMessage));
            try {
                Teacher addedTeacher = teacherService
                        .addTeacherToDatabase(nameField.getText(), surnameField.getText(), emailField.getText());

                Platform.runLater(() -> teacherView.addTeacherToTable(teachersTable, addedTeacher));

            } catch (IllegalArgumentException e) {

                Platform.runLater(() -> teacherView.showErrorMessage(errorMessage));
                System.out.println(e.getMessage());
            }
        });
        myTask.execute();
    }

    @FXML
    protected void removeTeacher(){
        MyTask myTask = new MyTask(() -> {
            Platform.runLater(() -> teacherView.hideErrorMessage(errorMessage));
            try {
                Teacher removedTeacher = teacherService
                        .removeTeacherFromDatabase(idField.getText());

                Platform.runLater(() -> teacherView.removeTeacherFromTable(teachersTable, removedTeacher));

            } catch (IllegalArgumentException e) {

                Platform.runLater(() -> teacherView.showErrorMessage(errorMessage));
                System.out.println(e.getMessage());
            }
        });
        myTask.execute();
    }

    @FXML
    protected void editTeacher(){
        MyTask myTask = new MyTask(() -> {
            Platform.runLater(() -> teacherView.hideErrorMessage(errorMessage));
            try {

                Teacher teacher = teachersTable.getItems()
                        .filtered(tr -> tr.getId() == Long.parseLong(idField.getText()))
                        .get(0);

                Teacher editedTeacher = teacherService
                        .editTeacher(teacher, nameField.getText(), surnameField.getText(), emailField.getText());

                Platform.runLater(() -> teacherView.editTeacherToTable(teachersTable, editedTeacher));

            } catch (IllegalArgumentException e) {

                Platform.runLater(() -> teacherView.showErrorMessage(errorMessage));
                System.out.println(e.getMessage());
            }
        });
        myTask.execute();
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

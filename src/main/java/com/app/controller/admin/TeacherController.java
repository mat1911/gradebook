package com.app.controller.admin;

import com.app.controller.CrudController;
import com.app.entity.Teacher;
import com.app.service.impl.TeacherService;
import com.app.view.TeacherView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherController extends CrudController<Teacher> implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TableView<Teacher> teachersTable;

    private TeacherView teacherView = new TeacherView();
    private TeacherService teacherService = new TeacherService();
    private ObservableList<Teacher> allTeachers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.allTeachers = FXCollections.observableList(teacherService.getAllObjects());

        FilteredList<Teacher> filtered = new FilteredList(allTeachers);

        Platform.runLater(() -> {
            teacherView.setObjectsInTable(teachersTable, filtered);
            initFields(teachersTable, allTeachers, teacherService, teacherView);
        });
    }

    @FXML
    protected void changeAccessibilityForAddingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, removeOkButton}, new Button[]{addOkButton});
        changeAccessibilityForFields(new TextField[]{idField}, new TextField[]{nameField, surnameField, emailField, passwordField});
    }

    @FXML
    protected void changeAccessibilityForRemovingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, addOkButton}, new Button[]{removeOkButton});
        changeAccessibilityForFields(new TextField[]{nameField, surnameField, emailField, passwordField}, new TextField[]{idField});
    }

    @FXML
    protected void changeAccessibilityForEditingNodes(){

        changeAccessibilityForButtons(new Button[]{removeOkButton, addOkButton}, new Button[]{editOkButton});
        changeAccessibilityForFields(new TextField[]{}, new TextField[]{idField, nameField, surnameField, emailField, passwordField});
    }
}

package com.app.controller.admin;

import com.app.app.AppContext;
import com.app.controller.CrudController;
import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.repository.impl.StudentGroupRepository;
import com.app.repository.impl.StudentRepository;
import com.app.service.impl.StudentsService;
import com.app.validator.impl.StudentValidator;
import com.app.view.StudentView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class StudentController extends CrudController<Student> {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Label groupNameLabel;

    @FXML
    private TableView<Student> studentTable;

    private StudentGroup studentGroup;

    private StudentView studentView = new StudentView();

    private StudentValidator studentValidator = new StudentValidator();

    private StudentRepository studentRepository = new StudentRepository();

    private StudentsService studentsService = new StudentsService(studentValidator, studentRepository);

    private ObservableList<Student> allStudents;


    public StudentController() {

        AppContext appContext = AppContext.getInstance();
        studentGroup = appContext.getStudentGroup();

        this.allStudents = FXCollections.observableArrayList(studentGroup.getStudents());

        FilteredList<Student> filtered = new FilteredList(FXCollections.observableArrayList(allStudents));

        Platform.runLater(() -> {
            groupNameLabel.setText(studentGroup.getName());
            studentView.setObjectsInTable(studentTable, filtered);
            initFields(studentTable, allStudents, studentsService, studentView);
        });
    }

    @FXML
    @Override
    protected void addNewObject(){

        performAction(() -> {

            System.out.println(studentGroup.getStudents());

            Student addedObject = studentsService
                    .addStudentsToDatabase(getFieldsForAdding(), studentGroup);

            allStudents.add(addedObject);

            Platform.runLater(() -> studentView.addObjectToTable(studentTable, addedObject));

        }, "Sprawdź poprawność pól!");
    }

    @FXML
    protected void changeAccessibilityForAddingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, removeOkButton}, new Button[]{addOkButton});
        changeAccessibilityForFields(new TextField[]{idField}, new TextField[]{nameField, surnameField});
    }

    @FXML
    protected void changeAccessibilityForRemovingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, addOkButton}, new Button[]{removeOkButton});
        changeAccessibilityForFields(new TextField[]{nameField, surnameField}, new TextField[]{idField});
    }

    @FXML
    protected void changeAccessibilityForEditingNodes(){

        changeAccessibilityForButtons(new Button[]{removeOkButton, addOkButton}, new Button[]{editOkButton});
        changeAccessibilityForFields(new TextField[]{}, new TextField[]{idField, nameField, surnameField});
    }



}

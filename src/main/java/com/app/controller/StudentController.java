package com.app.controller;

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


public class StudentController extends CrudController<Student> {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Label groupNameLabel;

    private StudentGroup studentGroup;


    @FXML
    private TableView<Student> studentTable;

    private StudentView studentView = new StudentView();

    private StudentValidator studentValidator = new StudentValidator();

    private StudentRepository studentRepository = new StudentRepository();

    private StudentsService studentsService = new StudentsService(studentValidator, studentRepository);

    private ObservableList<Student> allStudents;


    public StudentController() {

        this.allStudents = FXCollections.observableList(studentsService.getAllObjects());

        FilteredList<Student> filtered = new FilteredList(allStudents);

        Platform.runLater(() -> {
            studentGroup = (StudentGroup)model[0];
            groupNameLabel.setText(studentGroup.getName());
            studentView.setObjectsInTable(studentTable, filtered);
            initFields(studentTable, allStudents, studentsService, studentView);
        });
    }

    @FXML
    @Override
    protected void addNewObject(){

        performAction(() -> {

            StudentGroupRepository studentGroupRepository = new StudentGroupRepository();

            studentGroup = studentGroupRepository
                    .findOne(studentGroup.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Given group is not found in DB!"));

            Student addedObject = studentsService
                    .addStudentsToDatabase(getFieldsForAdding(), studentGroup);

            allStudents.add(addedObject);

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

package com.app.controller.admin;

import com.app.controller.CrudController;
import com.app.entity.Subject;
import com.app.repository.impl.SubjectRepository;
import com.app.service.impl.SubjectService;
import com.app.validator.impl.SubjectValidator;
import com.app.view.SubjectView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SubjectController extends CrudController<Subject> {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<Subject> subjectsTable;



    private SubjectView subjectView = new SubjectView();

    private SubjectRepository subjectRepository = new SubjectRepository();

    private SubjectValidator subjectValidator = new SubjectValidator();

    private SubjectService subjectService = new SubjectService(subjectValidator, subjectRepository);

    private ObservableList<Subject> allSubjects;

    public SubjectController() {

        this.allSubjects = FXCollections.observableList(subjectService.getAllObjects());

        FilteredList<Subject> filtered = new FilteredList(allSubjects);

        Platform.runLater(() -> {
            subjectView.setObjectsInTable(subjectsTable, filtered);
            initFields(subjectsTable, allSubjects, subjectService, subjectView);
        });

    }

    @FXML
    protected void changeAccessibilityForAddingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, removeOkButton}, new Button[]{addOkButton});
        changeAccessibilityForFields(new TextField[]{idField}, new TextField[]{nameField});
    }

    @FXML
    protected void changeAccessibilityForRemovingNodes(){

        changeAccessibilityForButtons(new Button[]{editOkButton, addOkButton}, new Button[]{removeOkButton});
        changeAccessibilityForFields(new TextField[]{nameField}, new TextField[]{idField});
    }

    @FXML
    protected void changeAccessibilityForEditingNodes(){

        changeAccessibilityForButtons(new Button[]{removeOkButton, addOkButton}, new Button[]{editOkButton});
        changeAccessibilityForFields(new TextField[]{}, new TextField[]{idField, nameField});
    }

}

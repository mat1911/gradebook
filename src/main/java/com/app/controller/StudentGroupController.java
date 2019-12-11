package com.app.controller;

import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.repository.impl.StudentGroupRepository;
import com.app.service.impl.GroupService;
import com.app.validator.impl.StudentGroupValidator;
import com.app.view.StudentGroupView;
import com.app.view.ViewManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

public class StudentGroupController extends CrudController<StudentGroup> {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<StudentGroup> groupsTable;

    private StudentGroupView studentGroupView = new StudentGroupView();

    private StudentGroupRepository studentGroupRepository = new StudentGroupRepository();

    private StudentGroupValidator studentGroupValidator = new StudentGroupValidator();

    private GroupService groupService = new GroupService(studentGroupValidator, studentGroupRepository);

    private ObservableList<StudentGroup> allGroups;

    public StudentGroupController() {

        this.allGroups = FXCollections.observableList(groupService.getAllObjects());

        FilteredList<StudentGroup> filtered = new FilteredList(allGroups);

        Platform.runLater(() -> {
            studentGroupView.setObjectsInTable(groupsTable, filtered);
            initFields(groupsTable, allGroups, groupService, studentGroupView);
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

    @FXML
    protected void addListenerToTable(){
        groupsTable.setRowFactory(tv -> {

            TableRow<StudentGroup> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    StudentGroup clickedRow = row.getItem();
                    ViewManager viewManager = new ViewManager();
                    viewManager.showView(ViewManager.WindowView.STUDENTS_VIEW, new StudentGroup[]{clickedRow});
                }
            });
            return row ;


        });

    }
}

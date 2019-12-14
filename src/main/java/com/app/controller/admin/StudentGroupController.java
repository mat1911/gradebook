package com.app.controller.admin;

import com.app.app.AppContext;
import com.app.controller.CrudController;
import com.app.controller.ViewManager;
import com.app.entity.StudentGroup;
import com.app.enums.SubViewType;
import com.app.repository.impl.StudentGroupRepository;
import com.app.service.impl.StudentGroupService;
import com.app.validator.impl.StudentGroupValidator;
import com.app.view.StudentGroupView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class StudentGroupController extends CrudController<StudentGroup> {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<StudentGroup> groupsTable;

    @FXML
    private VBox contentPane;

    private StudentGroupView studentGroupView = new StudentGroupView();

    private StudentGroupRepository studentGroupRepository = new StudentGroupRepository();

    private StudentGroupValidator studentGroupValidator = new StudentGroupValidator();

    private StudentGroupService studentGroupService = new StudentGroupService(studentGroupValidator, studentGroupRepository);

    private ObservableList<StudentGroup> allGroups;

    public StudentGroupController() {

        this.allGroups = FXCollections.observableList(studentGroupService.getAllObjects());

        FilteredList<StudentGroup> filtered = new FilteredList(allGroups);

        Platform.runLater(() -> {
            studentGroupView.setObjectsInTable(groupsTable, filtered);
            initFields(groupsTable, allGroups, studentGroupService, studentGroupView);
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
    protected void addListenerToTable(MouseEvent mouseEvent){

        if(!groupsTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 2
                && mouseEvent.getButton() == MouseButton.PRIMARY ){

            StudentGroup selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
            AppContext appContext = AppContext.getInstance();
            appContext.setStudentGroup(selectedGroup);

            ViewManager viewManager = new ViewManager();
            viewManager.changeSubView(SubViewType.A_STUDENT_VIEW);

        }
    }
}

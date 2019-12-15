package com.app.controller.teacher;

import com.app.app.AppContext;
import com.app.controller.ViewManager;
import com.app.entity.Student;
import com.app.entity.StudentGroup;
import com.app.enums.SubViewType;
import com.app.service.impl.StudentGroupService;
import com.app.service.impl.StudentService;
import com.app.utility.BackgroundTask;
import com.app.view.StudentGroupView;
import com.app.view.StudentView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class StudentGroupController {
    private AppContext appContext = AppContext.getInstance();
    private ViewManager viewManager = new ViewManager();

    private StudentGroupView studentGroupView = new StudentGroupView();
    private StudentGroupService studentGroupService = new StudentGroupService();
    private ObservableList<StudentGroup> allGroups;

    private StudentView studentView = new StudentView();
    private StudentService studentService = new StudentService();

    @FXML
    private TextField searchField;
    @FXML
    private TableView<StudentGroup> groupsTable;
    @FXML
    private Label chosenGroupNameLabel;
    @FXML
    private TableView<Student> studentTable;

    public StudentGroupController() {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            this.allGroups = FXCollections.observableList(studentGroupService.findByTeacher(appContext.getLoggedTeacher()));
            Platform.runLater(() -> studentGroupView.setObjectsInTable(groupsTable, new FilteredList<>(allGroups)));
        });
        backgroundTask.execute();
    }

    @FXML
    private void searchForObject(KeyEvent keyEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            FilteredList<StudentGroup> filteredObjects = studentGroupService
                    .filterObjects(allGroups, searchField.getText());

            Platform.runLater(() -> studentGroupView.setObjectsInTable(groupsTable, filteredObjects));
        });
        backgroundTask.execute();
    }

    @FXML
    private void chooseGroup(MouseEvent mouseEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!groupsTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                    && mouseEvent.getButton() == MouseButton.PRIMARY) {

                StudentGroup selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
                List<Student> students = studentService.findByGroup(selectedGroup);

                Platform.runLater(() -> {
                    chosenGroupNameLabel.setText(selectedGroup.getName());
                    studentView.setObjectsInTable(studentTable, new FilteredList<>(FXCollections.observableList(students)));
                });
            }
        });
        backgroundTask.execute();
    }

    @FXML
    private void chooseStudent(MouseEvent mouseEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!studentTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 2
                    && mouseEvent.getButton() == MouseButton.PRIMARY) {

                Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
                appContext.setSelectedStudent(selectedStudent);

                viewManager.changeSubView(SubViewType.T_STUDENT_VIEW);
            }
        });
        backgroundTask.execute();
    }
}

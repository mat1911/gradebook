package com.app.controller.teacher;

import com.app.app.AppContext;
import com.app.entity.Mark;
import com.app.entity.Remark;
import com.app.entity.Student;
import com.app.entity.Subject;
import com.app.service.MarkService;
import com.app.service.RemarkService;
import com.app.service.impl.SubjectService;
import com.app.utility.BackgroundTask;
import com.app.view.MarkView;
import com.app.view.RemarkView;
import com.app.view.SubjectView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class StudentController {
    private AppContext appContext = AppContext.getInstance();
    private Student selectedStudent;

    private MarkView markView = new MarkView();
    private MarkService markService = new MarkService();

    private SubjectView subjectView = new SubjectView();
    private SubjectService subjectService = new SubjectService();
    private ObservableList<Subject> allSubjects;

    private RemarkView remarkView = new RemarkView();
    private RemarkService remarkService = new RemarkService();
    private ObservableList<Remark> allRemarks;

    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private Label chosenStudentLabel;
    @FXML
    private TableView<Mark> markTable;
    @FXML
    private TableColumn<Mark, String> markTeacherCol;
    @FXML
    private Label chosenSubjectLabel;
    @FXML
    private Button marksButton;
    @FXML
    private TableView<Remark> remarkTable;
    @FXML
    private TableColumn<Remark, String> remarkDateCol;
    @FXML
    private TableColumn<Remark, String> remarkTeacherCol;

    public StudentController() {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            allSubjects = FXCollections.observableList(subjectService.findByStudent(appContext.getSelectedStudent()));
            Platform.runLater(() -> subjectView.setObjectsInTable(subjectTable, new FilteredList<>(allSubjects)));

            allRemarks = FXCollections.observableList(remarkService.findByStudent(appContext.getSelectedStudent()));
            Platform.runLater(() -> remarkView.setObjectsInTable(remarkTable, new FilteredList<>(allRemarks)));
        });
        backgroundTask.execute();
    }

    @FXML
    private void chooseSubject(MouseEvent mouseEvent) {
        if (!subjectTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                && mouseEvent.getButton() == MouseButton.PRIMARY) {

            Subject selectedSubject = subjectTable.getSelectionModel().getSelectedItem();
            List<Mark> marks = markService.findByStudentAndSubject(selectedStudent, selectedSubject);

            Platform.runLater(() -> {
                chosenSubjectLabel.setText(selectedSubject.getName());
                markView.setObjectsInTable(markTable, new FilteredList<>(FXCollections.observableList(marks)));
            });
        }
    }

    @FXML
    private void generatePdfWithMarks(){
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            try {
                markService.generateDocumentWithMarks(appContext.getSelectedStudent());
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        });
        backgroundTask.execute();
    }

    public void initialize() {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            setTablesPropertyValueFactories();

            selectedStudent = appContext.getSelectedStudent();
            Platform.runLater(() -> chosenStudentLabel.setText(
                    selectedStudent.getName() + " " + selectedStudent.getSurname() + " " + selectedStudent.getGroup().getName())
            );
        });
        backgroundTask.execute();
    }

    private void setTablesPropertyValueFactories() {
        markView.setPropertyValueFactories(markTeacherCol);
        remarkView.setPropertyValueFactories(remarkDateCol, remarkTeacherCol);
    }
}

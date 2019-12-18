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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.List;

public class StudentController {


    private AppContext appContext = AppContext.getInstance();
    private Student selectedStudent;
    private Subject selectedSubject;
    private Mark selectedMark;
    private Remark selectedRemark;

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
    private TableColumn<Mark, TextField> markDescCol;
    @FXML
    private TableColumn<Mark, String> markTeacherCol;
    @FXML
    private TableColumn<Mark, ComboBox<String>> markCol;
    @FXML
    private Label chosenSubjectLabel;
    @FXML
    private Button marksButton;
    @FXML
    private TableView<Remark> remarkTable;
    @FXML
    private TableColumn<Remark, String> remarkTeacherCol;
    @FXML
    private TableColumn<Remark, TextField> remarkContentCol;
    @FXML
    private Label remarkError;
    @FXML
    private Label markError;

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
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!subjectTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                    && mouseEvent.getButton() == MouseButton.PRIMARY) {

                selectedSubject = subjectTable.getSelectionModel().getSelectedItem();
                List<Mark> marks = markService.findByStudentAndSubject(selectedStudent, selectedSubject);


                Platform.runLater(() -> {
                    chosenSubjectLabel.setText(selectedSubject.getName());
                    markError.setText("");
                    markView.setObjectsInTable(markTable, new FilteredList<>(FXCollections.observableList(marks)));
                });
            }
        });
        backgroundTask.execute();
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
        remarkView.setPropertyValueFactories(remarkTeacherCol);

        remarkContentCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Remark, TextField>, ObservableValue<TextField>>() {
            @Override
            public ObservableValue<TextField> call(
                    TableColumn.CellDataFeatures<Remark, TextField> arg0) {
                Remark remark = arg0.getValue();
                TextField textField = new TextField();
                textField.textProperty().setValue(remark.getDescription());
                textField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        remark.setDescription(t1);
                        remarkService.update(remark);
                    }
                });
                return new SimpleObjectProperty<TextField>(textField);
            }
        });

        markDescCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mark, TextField>, ObservableValue<TextField>>() {
            @Override
            public ObservableValue<TextField> call(
                    TableColumn.CellDataFeatures<Mark, TextField> arg0) {
                Mark mark = arg0.getValue();
                TextField textField = new TextField();
                textField.textProperty().setValue(mark.getDescription());
                textField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        mark.setDescription(t1);
                        markService.update(mark);
                    }
                });
                return new SimpleObjectProperty<TextField>(textField);
            }
        });

        markCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mark, ComboBox<String>>, ObservableValue<ComboBox<String>>>() {
            @Override
            public ObservableValue<ComboBox<String>> call(TableColumn.CellDataFeatures<Mark, ComboBox<String>> markComboBoxCellDataFeatures) {
                Mark mark = markComboBoxCellDataFeatures.getValue();
                ComboBox<String> markBox = new ComboBox<>();
                markBox.getItems().addAll(
                        "1", "2", "3", "4", "5", "6"
                );
                markBox.valueProperty().setValue(mark.getMark());
                markBox.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        mark.setMark(t1);
                        markService.update(mark);
                    }
                });
                return new SimpleObjectProperty<ComboBox<String>>(markBox);
            }
        });


    }

    @FXML
    private void addMark(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if(selectedSubject == null) {
                Platform.runLater(() -> markError.setText("Nalezy wybrac przedmiot!"));
                return;
            }
            Mark mark = Mark.builder()
                    .mark("")
                    .description("")
                    .student(selectedStudent)
                    .subject(selectedSubject)
                    .teacher(appContext.getLoggedTeacher())
                    .build();
            markService.add(mark);
            Platform.runLater(() -> markView.addObjectToTable(markTable, mark));
        });
        backgroundTask.execute();
    }

    @FXML
    private void removeMark(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!markTable.getSelectionModel().isEmpty() && selectedMark != null) {

                markService.delete(selectedMark);
                Platform.runLater(() -> {
                    markView.removeObjectFromTable(markTable, selectedMark);
                    selectedMark = null;
                });
            }
        });
        backgroundTask.execute();
    }

    @FXML
    private void addRemark(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            Remark remark = Remark.builder()
                    .description("")
                    .student(selectedStudent)
                    .teacher(appContext.getLoggedTeacher())
                    .build();
            remarkService.add(remark);
            Platform.runLater(() -> remarkView.addObjectToTable(remarkTable, remark));
        });
        backgroundTask.execute();
    }

    @FXML
    private void removeRemark(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!remarkTable.getSelectionModel().isEmpty() && selectedRemark != null) {

                remarkService.delete(selectedRemark);
                Platform.runLater(() -> {
                    remarkView.removeObjectFromTable(remarkTable, selectedRemark);
                    selectedRemark = null;
                });
            }
        });
        backgroundTask.execute();
    }

    @FXML
    private void chooseRemark(MouseEvent mouseEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!remarkTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                    && mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectedRemark = remarkTable.getSelectionModel().getSelectedItem();
            }
        });
        backgroundTask.execute();
    }

    @FXML
    private void chooseMark(MouseEvent mouseEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            if (!markTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                    && mouseEvent.getButton() == MouseButton.PRIMARY) {
                selectedMark = markTable.getSelectionModel().getSelectedItem();
            }
        });
        backgroundTask.execute();
    }
}


package com.app.controller.teacher;

import com.app.app.AppContext;
import com.app.entity.Lesson;
import com.app.entity.Student;
import com.app.entity.Teacher;
import com.app.entity.Test;
import com.app.service.LessonService;
import com.app.service.TestService;
import com.app.service.impl.AttendanceService;
import com.app.service.impl.StudentService;
import com.app.utility.BackgroundTask;
import com.app.view.StudentView;
import com.app.view.TimetableView;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

import java.time.LocalDate;
import java.util.List;

public class TimetableController {
    private AppContext appContext = AppContext.getInstance();

    private TimetableView timetableView = new TimetableView();
    private LessonService lessonService = new LessonService();
    private ObservableList<Lesson> lessonsForTheDay;

    private StudentView studentView = new StudentView();
    private StudentService studentService = new StudentService();

    private TestService testService = new TestService();

    private AttendanceService attendanceService = new AttendanceService();

    private Teacher loggedTeacher;
    private Lesson selectedLesson;
    private LocalDate actualDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label dateLabel;
    @FXML
    private TableView<Lesson> lessonTable;
    @FXML
    private TableColumn<Lesson, String> subjectCol;
    @FXML
    private TableColumn<Lesson, String> groupCol;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, CheckBox> presenceCol;
    @FXML
    private TableColumn<Student, String> studentCol;
    @FXML
    private Label testLabel;
    @FXML
    private TextField testField;


    @FXML
    private void chooseLesson(MouseEvent mouseEvent) {
        if (!lessonTable.getSelectionModel().isEmpty() && mouseEvent.getClickCount() == 1
                && mouseEvent.getButton() == MouseButton.PRIMARY) {

            selectedLesson = lessonTable.getSelectionModel().getSelectedItem();
            List<Student> students = studentService.findByLesson(selectedLesson);

            Platform.runLater(() -> {
                testLabel.setText(selectedLesson.getTest() == null ? "Brak" : selectedLesson.getTest().getDescription());
                studentView.setObjectsInTable(studentTable, new FilteredList<>(FXCollections.observableList(students)));
            });
        }
    }

    @FXML
    private void dateChosen(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            actualDate = datePicker.getValue();
            updateLessonTable();
        });
        backgroundTask.execute();
    }

    @FXML
    private void dayBefore(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            actualDate = actualDate.minusDays(1);
            updateLessonTable();
        });
        backgroundTask.execute();
    }

    @FXML
    private void dayAfter(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            actualDate = actualDate.plusDays(1);
            updateLessonTable();
        });
        backgroundTask.execute();
    }

    public void initialize() {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            setTablesPropertyValueFactories();

            loggedTeacher = appContext.getLoggedTeacher();
            actualDate = LocalDate.now();

            updateLessonTable();
        });
        backgroundTask.execute();
    }

    private void setTablesPropertyValueFactories() {
        timetableView.setPropertyValueFactories(subjectCol, groupCol);
        studentCol.setCellValueFactory(student ->
                new SimpleStringProperty(
                        student.getValue().getName() + " " + student.getValue().getSurname())
        );


        presenceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, CheckBox>, ObservableValue<CheckBox>>() {
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Student, CheckBox> arg0) {
                Student student = arg0.getValue();
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().setValue(attendanceService.isPresent(student, selectedLesson));
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {
                        attendanceService.setPresence(student, selectedLesson, new_val);
                    }
                });
                return new SimpleObjectProperty<CheckBox>(checkBox);
            }
        });
    }

    private void updateLessonTable() {
        lessonsForTheDay = FXCollections.observableList(lessonService.findByTeacherAndDate(loggedTeacher, actualDate));
        Platform.runLater(() -> {
            timetableView.setLabelText(dateLabel, actualDate.toString());
            timetableView.setObjectsInTable(lessonTable, new FilteredList<>(lessonsForTheDay));
        });
    }

    @FXML
    private void addTest(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            Test test = Test.builder()
                    .description(testField.getText())
                    .lesson(selectedLesson)
                    .build();
            selectedLesson.setTest(test);
            testService.addOrUpdate(test);
            Platform.runLater(() -> testLabel.setText(testField.getText()));
        });
        backgroundTask.execute();
    }

    @FXML
    private void removeTest(ActionEvent actionEvent) {
        BackgroundTask backgroundTask = new BackgroundTask(() -> {
            testService.delete(selectedLesson.getTest());
            selectedLesson.setTest(null);
            lessonService.changeLesson(selectedLesson);
            Platform.runLater(() -> testLabel.setText("Brak"));
        });
        backgroundTask.execute();
    }
}

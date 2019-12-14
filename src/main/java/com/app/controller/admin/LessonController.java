package com.app.controller.admin;

import com.app.entity.Lesson;
import com.app.entity.StudentGroup;
import com.app.entity.Subject;
import com.app.entity.Teacher;
import com.app.repository.impl.LessonRepository;
import com.app.repository.impl.StudentGroupRepository;
import com.app.repository.impl.SubjectRepository;
import com.app.repository.impl.TeacherRepository;
import com.app.service.LessonService;
import com.app.service.generic.ActionPerformer;
import com.app.utility.BackgroundTask;
import com.app.validator.impl.LessonValidator;
import com.app.view.LessonView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class LessonController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox groupBox;

    @FXML
    private ChoiceBox teacherBox;

    @FXML
    private ChoiceBox subjectBox;

    @FXML
    private ChoiceBox lessonNumberBox;

    @FXML
    private TableView lessonTable;

    @FXML
    private Label errorLabel;

    private StudentGroupRepository studentGroupRepository = new StudentGroupRepository();
    private TeacherRepository teacherRepository = new TeacherRepository();
    private SubjectRepository subjectRepository = new SubjectRepository();
    private LessonRepository lessonRepository = new LessonRepository();
    private LessonService lessonService = new LessonService();
    private LessonView lessonView = new LessonView();
    private LessonValidator lessonValidator = new LessonValidator();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {

            lessonTable.setItems(FXCollections.observableArrayList());
            groupBox.setItems(FXCollections.observableArrayList(studentGroupRepository.findAll()));
            teacherBox.setItems(FXCollections.observableArrayList(teacherRepository.findAll()));
            subjectBox.setItems(FXCollections.observableArrayList(subjectRepository.findAll()));
            lessonNumberBox.setItems(FXCollections.observableArrayList());

            groupBox.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> loadLessons());

            IntStream.range(1, 9).forEach(num -> lessonNumberBox.getItems().add(num));

        });
    }

    @FXML
    protected void loadLessons() {

        performAction(() -> {
            if (datePicker.getValue() != null && groupBox.getValue() != null) {
                Platform.runLater(() -> {
                    lessonTable.setItems(FXCollections
                            .observableArrayList(lessonRepository
                                    .findAllByDateAndGroup(datePicker.getValue(), (StudentGroup) groupBox.getValue())));
                });

            }
        }, null);
    }

    @FXML
    protected void changeLesson() {

        performAction(() -> {
            Lesson lesson = Lesson.builder()
                    .date(datePicker.getValue())
                    .group((StudentGroup) groupBox.getValue())
                    .teacher((Teacher) teacherBox.getValue())
                    .subject((Subject) subjectBox.getValue())
                    .lessonNumber(Long.parseLong(lessonNumberBox.getValue().toString()))
                    .build();

            lessonValidator.validate(lesson);

            if (lessonValidator.hasErrors()){
                throw new IllegalArgumentException("Some fields are not valid!");
            }

            Lesson changedLesson = lessonService.changeLesson(lesson);

            lessonView.updateLessonTable(lessonTable, changedLesson);
        }, "Nie można dodać lekcji. Być może nauczyciel ma zajęcia w tym czasie lub nie uzupełniono wszystkich pól.");

    }

    private void performAction(ActionPerformer actionPerformer, String errorMessage) {

        BackgroundTask backgroundTask = new BackgroundTask(() -> {

            Platform.runLater(() -> lessonView.hideErrorMessage(errorLabel));
            try {
                actionPerformer.perform();
            } catch (Exception e) {
                Platform.runLater(() -> lessonView.showErrorMessage(errorLabel, errorMessage));
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
        backgroundTask.execute();
    }


}

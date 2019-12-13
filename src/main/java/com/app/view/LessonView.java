package com.app.view;

import com.app.entity.Lesson;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class LessonView {

    public void updateLessonTable(TableView<Lesson> lessonTable, Lesson lessonToChange){

        lessonTable
                .getItems()
                .removeIf(lesson -> lesson.getLessonNumber().equals(lessonToChange.getLessonNumber()));

        lessonTable.getItems().add(lessonToChange);

        lessonTable.refresh();
    }

    public void hideErrorMessage(Label errorMessageLabel) {

        errorMessageLabel.setVisible(false);
    }

    public void showErrorMessage(Label errorMessageLabel, String errorMessage) {

        errorMessageLabel.setText(errorMessage);
        errorMessageLabel.setVisible(true);
    }

}

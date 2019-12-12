package com.app.view;

import com.app.entity.Lesson;
import javafx.scene.control.TableView;

public class LessonView {

    public void updateLessonTable(TableView<Lesson> lessonTable, Lesson lessonToChange){

        lessonTable
                .getItems()
                .removeIf(lesson -> lesson.getLessonNumber().equals(lessonToChange.getLessonNumber()));

        lessonTable.getItems().add(lessonToChange);

        lessonTable.refresh();
    }

}

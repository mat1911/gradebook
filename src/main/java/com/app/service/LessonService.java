package com.app.service;

import com.app.entity.Lesson;
import com.app.repository.impl.LessonRepository;

import java.util.Optional;

public class LessonService {

    private LessonRepository lessonRepository = new LessonRepository();

    public Lesson changeLesson(Lesson lesson){

        Lesson changedLesson;

        Optional<Lesson> optionalLesson = lessonRepository
                .findWithDateAndGroupAndLessonNumber(lesson.getDate(), lesson.getGroup(), lesson.getLessonNumber());

        if(!optionalLesson.isPresent()) {
            changedLesson = lessonRepository.add(lesson).get();
        }else {
            lesson.setId(optionalLesson.get().getId());
            changedLesson = lessonRepository.update(lesson).get();
        }

        return changedLesson;
    }


}

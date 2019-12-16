package com.app.service;

import com.app.entity.Lesson;
import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import com.app.repository.impl.LessonRepository;
import com.app.validator.impl.LessonValidator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LessonService {

    private LessonRepository lessonRepository = new LessonRepository();
    private LessonValidator lessonValidator = new LessonValidator();

    public Lesson changeLesson(Lesson lesson) {

        Lesson changedLesson;

        lessonValidator.validate(lesson);

        if (lessonValidator.hasErrors()){
            throw new IllegalArgumentException("Some fields are not valid!");
        }

        Optional<Lesson> optionalLesson = lessonRepository
                .findByDateAndGroupAndLessonNumber(lesson.getDate(), lesson.getGroup(), lesson.getLessonNumber());

        if (checkIfTeacherHasLessonsWithOtherGroup(lesson)) {
            throw new IllegalArgumentException("Teacher already has lesson with other group during this hour!");
        }

        if (!optionalLesson.isPresent()) {
            changedLesson = lessonRepository.add(lesson).get();

        } else {
            lesson.setId(optionalLesson.get().getId());
            changedLesson = lessonRepository.update(lesson).get();
        }

        return changedLesson;
    }


    public List<Lesson> findByTeacher(Teacher loggedTeacher) {
        List<Lesson> lessons = lessonRepository.findByTeacher(loggedTeacher);
        return lessons.isEmpty() ? Collections.emptyList() : lessons;
    }

    public List<Lesson> findByGroup(StudentGroup group) {
        List<Lesson> lessons = lessonRepository.findByGroup(group);
        return lessons.isEmpty() ? Collections.emptyList() : lessons;
    }

    public List<Lesson> findAllByDateAndGroup(LocalDate date, StudentGroup group){
        return lessonRepository.findAllByDateAndGroup(date, group);
    }

    private boolean checkIfTeacherHasLessonsWithOtherGroup(Lesson lessonToAdd) {

        return lessonRepository
                .findByTeacher(lessonToAdd.getTeacher())
                .stream()
                .anyMatch(lesson -> lesson.getLessonNumber().equals(lessonToAdd.getLessonNumber())
                        && lesson.getDate().equals(lessonToAdd.getDate())
                        && !lesson.getGroup().getId().equals(lessonToAdd.getGroup().getId()));

    }

    public List<Lesson> findByTeacherAndDate(Teacher loggedTeacher, LocalDate actualDate) {
        return lessonRepository.findByTeacherAndDate(actualDate, loggedTeacher);
    }
}

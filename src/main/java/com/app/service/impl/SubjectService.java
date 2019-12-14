package com.app.service.impl;

import com.app.entity.*;
import com.app.repository.generic.CrudRepository;
import com.app.service.LessonService;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.generic.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectService extends AbstractCrudService<Subject> implements CrudService<Subject> {

    public SubjectService(Validator<Subject> validator, CrudRepository<Subject, Long> crudRepository) {
        super(validator, crudRepository);
    }

    public List<Subject> findByStudent(Student selectedStudent) {
        List<Lesson> studentLessons = new LessonService().findByGroup(selectedStudent.getGroup());
        System.out.println(studentLessons.toString());
        List<Subject> studentSubjects = new ArrayList<>();

        for(Lesson lesson : studentLessons) {
            Subject check = getSubjectByLesson(lesson);
            if(!studentSubjects.contains(check)) {
                studentSubjects.add(check);
            }
        }

        return studentSubjects.isEmpty() ? Collections.emptyList() : studentSubjects;
    }

    private Subject getSubjectByLesson(Lesson lesson) {
        List<Subject> subjects = getAllObjects();
        for(Subject subject : subjects) {
            if(subject.getLessons().contains(lesson))
                return subject;
        }
        throw new IllegalArgumentException("There is no subject associated with such lesson!");
    }
}

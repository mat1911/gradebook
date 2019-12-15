package com.app.service.impl;

import com.app.entity.*;
import com.app.repository.impl.SubjectRepository;
import com.app.service.LessonService;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.impl.SubjectValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubjectService extends AbstractCrudService<Subject> implements CrudService<Subject> {

    private SubjectRepository subjectRepository = new SubjectRepository();
    private SubjectValidator subjectValidator = new SubjectValidator();

    public SubjectService(){
        super.initialize(subjectValidator, subjectRepository);
    }

    public List<Subject> findByStudent(Student selectedStudent) {
        List<Lesson> studentLessons = new LessonService().findByGroup(selectedStudent.getGroup());
        List<Subject> studentSubjects = new ArrayList<>();
        for(Lesson lesson : studentLessons) {
            Subject check = getSubjectByLesson(lesson);
            if(!studentSubjects.contains(check)) {
                studentSubjects.add(check);
            }
        }

        return studentSubjects.isEmpty() ? Collections.emptyList() : studentSubjects;
    }

    public List<Subject> findAll(){
        return subjectRepository.findAll();
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

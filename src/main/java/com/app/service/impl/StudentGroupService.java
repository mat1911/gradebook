package com.app.service.impl;

import com.app.entity.Lesson;
import com.app.entity.StudentGroup;
import com.app.entity.Teacher;
import com.app.repository.impl.StudentGroupRepository;
import com.app.service.LessonService;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.impl.StudentGroupValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentGroupService extends AbstractCrudService<StudentGroup> implements CrudService<StudentGroup> {

    private StudentGroupValidator studentGroupValidator = new StudentGroupValidator();
    private StudentGroupRepository studentGroupRepository = new StudentGroupRepository();

    public StudentGroupService() {
        super.initialize(studentGroupValidator, studentGroupRepository);
    }

    public List<StudentGroup> findByTeacher(Teacher loggedTeacher) {
        List<Lesson> teacherLessons = new LessonService().findByTeacher(loggedTeacher);
        List<StudentGroup> studentGroups = new ArrayList<>();

        for(Lesson lesson : teacherLessons) {
            StudentGroup check = getGroupByLesson(lesson);
            if(!studentGroups.contains(check)) {
                studentGroups.add(check);
            }
        }

        return studentGroups.isEmpty() ? Collections.emptyList() : studentGroups;
    }

    public StudentGroup getGroupByLesson(Lesson lesson) {
        List<StudentGroup> studentGroups = getAllObjects();
        for(StudentGroup group : studentGroups) {
            if(group.getLessons().contains(lesson))
                return group;
        }
        throw new IllegalArgumentException("There is no group associated with such lesson!");
    }

    public List<StudentGroup> findAll(){
        return studentGroupRepository.findAll();
    }
}

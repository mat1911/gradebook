package com.app.service.impl;

import com.app.entity.Teacher;
import com.app.repository.impl.TeacherRepository;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.impl.TeacherValidator;

import java.util.List;
import java.util.Optional;


public class TeacherService extends AbstractCrudService<Teacher> implements CrudService<Teacher> {

    private TeacherRepository teacherRepository = new TeacherRepository();
    private TeacherValidator teacherValidator = new TeacherValidator();

    public TeacherService() {
        super.initialize(teacherValidator, teacherRepository);
    }

    public Teacher findByEmail(String email) {
        Optional<Teacher> teacher = ((TeacherRepository)crudRepository).findByEmail(email);
        if(teacher.isPresent())
            return teacher.get();
        throw new IllegalArgumentException("No teacher associated with such e-mail!");
    }

    public List<Teacher> findAll(){
        return teacherRepository.findAll();
    }
}

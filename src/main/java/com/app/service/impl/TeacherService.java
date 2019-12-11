package com.app.service.impl;

import com.app.entity.Teacher;
import com.app.repository.generic.CrudRepository;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.generic.Validator;


public class TeacherService extends AbstractCrudService<Teacher> implements CrudService<Teacher> {

    public TeacherService(Validator<Teacher> validator, CrudRepository<Teacher, Long> crudRepository) {
        super(validator, crudRepository);
    }
}

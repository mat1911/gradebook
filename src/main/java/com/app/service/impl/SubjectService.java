package com.app.service.impl;

import com.app.entity.Subject;
import com.app.repository.generic.CrudRepository;
import com.app.service.generic.AbstractCrudService;
import com.app.service.generic.CrudService;
import com.app.validator.generic.Validator;

public class SubjectService extends AbstractCrudService<Subject> implements CrudService<Subject> {

    public SubjectService(Validator<Subject> validator, CrudRepository<Subject, Long> crudRepository) {
        super(validator, crudRepository);
    }
}
